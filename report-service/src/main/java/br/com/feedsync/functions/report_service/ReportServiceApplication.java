package br.com.feedsync.functions.report_service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;

public class ReportServiceApplication implements HttpFunction {

	private static final Logger log = LoggerFactory.getLogger(ReportServiceApplication.class);

	@Override
	public void service(HttpRequest request, HttpResponse response) throws Exception {

		Firestore db = FirebaseConfig.getFirestore();

		Map<String, Object> report = new HashMap<>();

		report.put("teste", "REPORT FUNCIONANDO !!!");

		db.collection("reports").add(report).get();
		log.info("Report salvo com sucesso no Firestore.");

		BufferedWriter writer = response.getWriter();
		writer.write("Report salvo com sucesso !!!");

	}

}
