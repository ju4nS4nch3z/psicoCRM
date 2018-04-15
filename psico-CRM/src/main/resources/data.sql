/*QUESTIONNAIRES*/
INSERT INTO QUESTIONNAIRES (id, name, description) VALUES(1, 'Suicidio', 'Cuestionario Suicidio') ON CONFLICT (id) DO NOTHING;
INSERT INTO QUESTIONNAIRES (id, name, description) VALUES(2, 'Maltrato', 'Cuestionario Maltrato') ON CONFLICT (id) DO NOTHING;
INSERT INTO QUESTIONNAIRES (id, name, description) VALUES(3, 'Radicalitzación', 'Cuestionario Radicalitzación')  ON CONFLICT (id) DO NOTHING;
