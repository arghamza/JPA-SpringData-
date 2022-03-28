package ma.emsi.hospital;

import ma.emsi.hospital.entities.*;
import ma.emsi.hospital.repositories.ConsultationRepository;
import ma.emsi.hospital.repositories.MedecinRepository;
import ma.emsi.hospital.repositories.PatientRepository;
import ma.emsi.hospital.repositories.RendezVousRepository;
import ma.emsi.hospital.service.IHospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class HospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
	}

	@Bean
	CommandLineRunner start(IHospitalService hospitalService,
							PatientRepository patientRepository,
							RendezVousRepository rendezvousRepository,
							MedecinRepository medecinRepository){
		return args ->{
			Stream.of("Mohamed","Hassan","Najat").forEach(
					name->{
						Patient patient=new Patient();
						patient.setNom(name);
						patient.setDateNaissance(new Date());
						patient.setMalade(false);
						hospitalService.savePatient(patient);
					}
			);
			Stream.of("Aymane","Hanane","Yasmine").forEach(
					name->{
						Medecin medecin=new Medecin();
						medecin.setNom(name);
						medecin.setSpecialite(Math.random()>0.5?"Cardio":"Dentiste");
						medecin.setEmail(name+"@email.com");
						hospitalService.saveMedecin(medecin);
					}
			);
				Patient patient=patientRepository.findById(1L).orElse(null);
				Patient patient1=patientRepository.findByNom("Mohamed");

				Medecin medecin=medecinRepository.findByNom("Yasmine");

				RendezVous rendezVous=new RendezVous();
				rendezVous.setDate(new Date());
				rendezVous.setStatus(StatusRDV.PENDING);
				rendezVous.setPatient(patient);
				rendezVous.setMedecin(medecin);
				RendezVous saveDRDV=hospitalService.saveRDV(rendezVous);
				System.out.println(saveDRDV.getId());

				RendezVous rendezVous1=rendezvousRepository.findAll().get(0);
				Consultation consultation=new Consultation();
				consultation.setDateConsultation(new Date());
				consultation.setRendezVous(rendezVous1);
				consultation.setRapport("Rapport de la consultation ....");
				hospitalService.saveConsultation(consultation);
		};
	}
}
