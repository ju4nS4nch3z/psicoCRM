package com.psicocrm.service;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.psicocrm.model.Administrator;
import com.psicocrm.model.Questionnaire_Done;
import com.psicocrm.model.Teacher;
import com.psicocrm.model.User;

@Service
public class EmailService {

	private JavaMailSender javaMailSender;
	@Autowired
	private MessageSource messageSource;

	public enum TypeEmail {
		NEW_USER("templates/email.html"), NEW_PASSWORD("templates/email.html"), QUESTIONNAIRE("templates/email.html");

		private final String template;

		TypeEmail(String template) {
			this.template = template;
		}

		public String getTypeEmail() {
			return this.template;
		}
	}

	@Autowired
	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendMail(List<String> to, String subject, MimeMultipart mimeMultipart) {

		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			message.setContent(mimeMultipart);

			for (String email : to) {
				InternetAddress iaRecipient = new InternetAddress(email);
				message.setRecipient(MimeMessage.RecipientType.BCC, iaRecipient);
			}

			message.setSubject(subject);

			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public JsonObject sendPassword(TypeEmail t, User user, String pass, String url, Locale locale) {

		JsonObject js = new JsonObject();
		List<String> bcc = new ArrayList<String>();
		VelocityContext vcContext = new VelocityContext();
		MimeBodyPart textBodyPart = new MimeBodyPart();
		MimeMultipart mimeMultipart = new MimeMultipart();
		String subject = null;
		try {

			if (t.equals(TypeEmail.NEW_USER)) {
				subject = messageSource.getMessage("mail.newUser.asunto", null, locale);
			}
			if (t.equals(TypeEmail.NEW_PASSWORD)) {
				subject = messageSource.getMessage("mail.resetPassword.asunto", null, locale);
			}

			if (user.isAdmin()) {
				String[] args = { ((Administrator) user).getSchool(), user.getMail(), pass };
				vcContext.put("title", messageSource.getMessage("mail.newUser.title", args, locale));
				vcContext.put("line1", messageSource.getMessage("mail.newUser.line1", args, locale));
				vcContext.put("line2", messageSource.getMessage("mail.newUser.line2", args, locale));
				vcContext.put("line3", messageSource.getMessage("mail.newUser.line3", args, locale));
			} else {
				String[] args = { ((Teacher) user).getName(), pass };
				vcContext.put("title", messageSource.getMessage("mail.resetPassword.title", args, locale));
				vcContext.put("line1", messageSource.getMessage("mail.resetPassword.line1", args, locale));
				vcContext.put("line2", messageSource.getMessage("mail.resetPassword.line2", args, locale));
				vcContext.put("line3", messageSource.getMessage("mail.resetPassword.line3", args, locale));
			}

			vcContext.put("url", url);
			vcContext.put("btnTxt", messageSource.getMessage("mail.newUser.btnTxt", null, locale));
			vcContext.put("displayUrl", "block");
			
			bcc.add(user.getMail());

			textBodyPart.setContent(getContent(t, vcContext, locale.getLanguage()).toString(),
					"text/html; charset=utf-8");

			mimeMultipart.addBodyPart(textBodyPart);

			sendMail(bcc, subject, mimeMultipart);
			js.addProperty("success", "se ha enviado");
		} catch (Exception e) {
			js.addProperty("error", "no se ha enviado");
		}

		return js;

	}

	public StringWriter getContent(TypeEmail t, VelocityContext vcContext, String locale) {
		VelocityEngine veEngine = new VelocityEngine();

		StringWriter swStringWriter = new StringWriter();

		try {
			veEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			veEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			veEngine.init();
			Template teTemplate = veEngine.getTemplate(t.getTypeEmail(), "UTF-8");
			teTemplate.merge(vcContext, swStringWriter);
			return swStringWriter;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public JsonObject sendQuestionnaire(Questionnaire_Done qdone, String emails, boolean sendParents,
			ByteArrayResource resource, Locale locale) {

		JsonObject js = new JsonObject();
		List<String> bcc = new ArrayList<String>();
		try {
			// TODO
			bcc.add(emails);

			VelocityContext vcContext = new VelocityContext();
			vcContext.put("q", qdone.getQuestionnaire().getDescription());
			vcContext.put("title", messageSource.getMessage("mail.questionnaire.title", null, locale));
			vcContext.put("line1", messageSource.getMessage("mail.questionnaire.line1", null, locale));
			vcContext.put("line2", messageSource.getMessage("mail.questionnaire.line2", null, locale));
			vcContext.put("line3", messageSource.getMessage("mail.questionnaire.line3", null, locale));
			vcContext.put("displayUrl", "none");

			byte[] bytes = resource.getByteArray();
			// construct the pdf body part
			DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
			MimeBodyPart pdfBodyPart = new MimeBodyPart();
			pdfBodyPart.setDataHandler(new DataHandler(dataSource));
			String date = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(qdone.getDate());
			String filename = qdone.getQuestionnaire().getDescription() + "_" + date;
			pdfBodyPart.setFileName(filename + ".pdf");

			// construct the text body part
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setContent(getContent(TypeEmail.QUESTIONNAIRE, vcContext, locale.getLanguage()).toString(),
					"text/html; charset=utf-8");

			// construct the mime multi part
			MimeMultipart mimeMultipart = new MimeMultipart();
			mimeMultipart.addBodyPart(textBodyPart);
			mimeMultipart.addBodyPart(pdfBodyPart);

			sendMail(bcc, messageSource.getMessage("resultado", null, locale) + " " + filename, mimeMultipart);

			js.addProperty("success", "se ha enviado");
		} catch (Exception e) {
			js.addProperty("error", "no se ha enviado");
		}

		return js;
	}
}
