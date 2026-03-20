package com.example.tiffin_center_management.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.tiffin_center_management.model.Subscription;
import com.example.tiffin_center_management.repository.SubscriptionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TiffinScheduler {

	private final SubscriptionRepository subscriptionRepo;

	@Scheduled(cron = "0 0 0 * * *") // Roz raat 12 baje
	public void resetDailyTiffinStatus() {
	    LocalDate today = LocalDate.now();
	    
	    // Sirf wahi dhoondo jo COMPLETED hain
	    List<Subscription> subsToReset = subscriptionRepo.findAll().stream()
	            .filter(s -> "COMPLETED".equals(s.getStatus()) || "ACTIVE".equals(s.getStatus()))
	            .toList();

	    for (Subscription sub : subsToReset) {
	        if (sub.getEndDate().isBefore(today)) {
	            sub.setStatus("EXPIRED");
	        } else {
	            sub.setStatus("ACTIVE"); // Reset for next day
	        }
	    }
	    subscriptionRepo.saveAll(subsToReset);
	}
	
//    // Ye Cron Expression "0 0 0 * * *" ka matlab hai: Roz Raat 12:00:00 AM
////    @Scheduled(cron = "0 0 0 * * *")
//	@Scheduled(fixedRate = 60000)
//    public void resetDailyTiffinStatus() {
//        LocalDate today = LocalDate.now();
//
//        // 1. Un sabhi subscriptions ko dhoondo jo aaj "COMPLETED" hain aur expiry date abhi baki hai
//        List<Subscription> activeSubscriptions = subscriptionRepo.findAll();
//
//        for (Subscription sub : activeSubscriptions) {
//            // Check: Agar status COMPLETED hai aur endDate aaj ya aaj ke baad ki hai
//            if ("COMPLETED".equals(sub.getStatus()) && !sub.getEndDate().isBefore(today)) {
//                sub.setStatus("ACTIVE"); // Kal ke liye firse ACTIVE kar do
//            } 
//            // Check: Agar endDate nikal gayi hai, toh EXPIRED kar do
//            else if (sub.getEndDate().isBefore(today)) {
//                sub.setStatus("EXPIRED");
//            }
//        }
//
//        subscriptionRepo.saveAll(activeSubscriptions);
//        System.out.println("Tiffin Status Reset Task Completed for: " + today);
//    }
//	
}
