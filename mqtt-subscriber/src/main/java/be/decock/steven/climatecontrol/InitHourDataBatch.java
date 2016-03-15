package be.decock.steven.climatecontrol;

import be.decock.steven.Application;
import be.decock.steven.climatecontrol.data.ClimateDataInstantRepository;
import be.decock.steven.climatecontrol.service.ClimateDataHourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class InitHourDataBatch {

    private static Logger logger = LoggerFactory.getLogger(InitHourDataBatch.class);

    private int numberOfDataInstantsProcessed = 0;

    @Autowired
    private ClimateDataHourService climateDataHourService;

    @Autowired
    private ClimateDataInstantRepository climateDataInstantRepository;

    public void initHourData() {
        climateDataInstantRepository.findAllByOrderByTimeAsc().stream()
                .forEach(instant -> {
                    climateDataHourService.handleClimateDataInstant(instant);
                    numberOfDataInstantsProcessed++;
                    if (numberOfDataInstantsProcessed % 1000 == 0) {
                        logger.info("Processed " + numberOfDataInstantsProcessed + " instants...");
                    }
                });
    }

    public static void main(String[] args) {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(Application.class);

        InitHourDataBatch initHourDataBatch = ctx.getBean(InitHourDataBatch.class);
        initHourDataBatch.initHourData();
    }

}
