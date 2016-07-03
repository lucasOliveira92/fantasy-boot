package com.fantasy;


import com.fantasy.Services.GenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup
        implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */

    @Autowired
    GenerateService generateService;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        try {
            generateService.populateRealTeamsPlayers();
            generateService.populateVirtualTeamsUsers();
            generateService.genererateRandomSnapshots(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }

} // class