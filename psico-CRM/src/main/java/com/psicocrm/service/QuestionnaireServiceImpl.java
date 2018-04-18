package com.psicocrm.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;
import java.util.logging.XMLFormatter;

import org.apache.jasper.xmlparser.XMLEncodingDetector;
import org.hibernate.internal.util.xml.XMLHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RadioCheckField;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.psicocrm.dao.AnswerDAO;
import com.psicocrm.dao.OptionDAO;
import com.psicocrm.dao.QuestionDAO;
import com.psicocrm.dao.QuestionnaireDAO;
import com.psicocrm.dao.Questionnaire_DoneDAO;
import com.psicocrm.model.Answer;
import com.psicocrm.model.Option;
import com.psicocrm.model.Question;
import com.psicocrm.model.Questionnaire;
import com.psicocrm.model.Questionnaire_Done;

@Service
public class QuestionnaireServiceImpl implements QuestionaireService {
	private static final int PAGE_SIZE = 10;

	@Autowired
	private Questionnaire_DoneDAO questionnaire_DoneRepository;

	@Autowired
	private QuestionnaireDAO questionnaireRepository;

	@Autowired
	private AnswerDAO answerRepository;

	@Autowired
	private OptionDAO optionRepository;

	@Autowired
	private QuestionDAO questionRepository;

	@Autowired
	private MessageSource messageSource;

	@Override
	public Page<Questionnaire_Done> getStudentQuestionnaires(int pageNum, long questionnaire_id, long student_id) {

		Pageable pageable = new PageRequest(pageNum - 1, PAGE_SIZE, Sort.Direction.DESC, "date");
		if (questionnaire_id > 0) {
			return questionnaire_DoneRepository.findByStudent_IdAndQuestionnaire_Id(student_id, questionnaire_id,
					pageable);
		} else {
			return questionnaire_DoneRepository.findByStudent_Id(student_id, pageable);
		}
	}

	@Override
	public Questionnaire getQuestionnaireById(long id) {
		return questionnaireRepository.findOne(id);
	}

	@Override
	public void saveQD(Questionnaire_Done questionnaire_done) {
		questionnaire_DoneRepository.save(questionnaire_done);

	}

	@Override
	public void saveAnswer(Answer answer) {
		answerRepository.save(answer);
	}

	@Override
	public Option getOptionById(Long id) {
		return optionRepository.findOne(id);
	}

	@Override
	public Question getQuestionById(Long id) {
		return questionRepository.findOne(id);
	}

	@Override
	public Set<Question> getQuestionsByIdAndLanguage(Long id, String language) {
		return questionRepository.getQuestionsByQuestionnaire_IdAndLanguage(id, language);
	}

	@Override
	public Questionnaire_Done getQuestionnaireDoneById(Long qDoneId) {
		return questionnaire_DoneRepository.findOne(qDoneId);
	}

	@Override
	public byte[] writePDF(Questionnaire_Done qdone, Locale locale) throws DocumentException, IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		Document document = new Document();
		PdfWriter.getInstance(document, outputStream);

		Font bold15 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15);
		Font bold12 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
		Font norm12 = FontFactory.getFont(FontFactory.HELVETICA, 12);
		
		String res = "";
		if (isBetween(qdone.getResult(), 0, 20)) {
			res = "Nor";
		} else if (isBetween(qdone.getResult(), 21, 30)) {
			res = "Seg";
		} else if (isBetween(qdone.getResult(), 31, 40)) {
			res = "Rie";
		}

		document.open();

		document.add(new Paragraph(20, qdone.getQuestionnaire().getDescription(), bold15));

		String nombre = messageSource.getMessage("form.nombre", null, locale) + ": " + qdone.getStudent().getName();
		String genero = messageSource.getMessage("form.genero", null, locale) + ": " + qdone.getStudent().getGender();
		String fechaNac = messageSource.getMessage("form.fechaNac", null, locale) + ": "
				+ qdone.getStudent().getBirdthDate();
		String resul = messageSource.getMessage("resultado", null, locale) + ": "
				+ messageSource.getMessage("resul" + res, null, locale) + " (" + qdone.getResult() + ")";

		document.add(new Paragraph(30, nombre, norm12));
		document.add(new Paragraph(genero, norm12));
		document.add(new Paragraph(fechaNac, norm12));
		document.add(new Paragraph(30, resul, norm12));
		
		document.add(new Paragraph(20, ""));
		
		String html = messageSource.getMessage("resultQ" + qdone.getQuestionnaire().getId() + res, null, locale);

		ElementList list = XMLWorkerHelper.parseToElementList(html, null);
		for (Element element : list) {
			document.add(element);
		}

		document.add(new Paragraph(20));

		for (Question q : getQuestionsByIdAndLanguage(qdone.getQuestionnaire().getId(), locale.getLanguage())) {

			document.add(new Paragraph(30, q.getText(), bold12));

			for (Option o : q.getOptions()) {
				Paragraph po = new Paragraph();
				Chunk c = new Chunk(o.getText());
				if (qdone.getAnswers().get(q.getPosition() - 1).getOption().equals(o)) {
					c.setBackground(BaseColor.YELLOW);
				}
				po.add(c);
				po.setIndentationLeft(20);
				document.add(po);
			}

		}

		document.close();

		return outputStream.toByteArray();
	}

	public static boolean isBetween(int x, int lower, int upper) {
		return lower <= x && x <= upper;
	}

}
