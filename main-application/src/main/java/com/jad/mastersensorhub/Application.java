package com.jad.mastersensorhub;

import com.jad.datamanagement.IDataManager;
import com.jad.reportgeneration.IReportGenerator;
import com.jad.reportgeneration.ReportType;
import com.jad.sensordata.ISensorFactory;
import com.jad.sharedmodel.SensorType;
import com.jad.userinterface.IApplication;
import com.jad.userinterface.IUserInterface;
import com.jad.userinterface.UserAction;
import com.jad.utils.Utils;

public class Application implements IApplication {
    private IUserInterface userInterface;
    private IDataManager dataManager;
    private ISensorFactory sensorFactory;
    private IReportGenerator reportGenerator;

    public void setUserInterface(final IUserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public void setSensorFactory(final ISensorFactory sensorFactory) {
        this.sensorFactory = sensorFactory;
    }

    public void setReportGenerator(final IReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    public void start() {
        Utils.LOGGER.logInfo("Application started");
        this.userInterface.start();
    }

    @Override
    public void manageOrder(UserAction userAction) {
        switch (userAction) {
            case ADD_HUMIDITY_SENSOR:
                this.dataManager.addDataCollector(this.sensorFactory.make(SensorType.HUMIDITY));
                break;
            case ADD_PRESSURE_SENSOR:
                this.dataManager.addDataCollector(this.sensorFactory.make(SensorType.PRESSURE));
                break;
            case ADD_TEMPERATURE_SENSOR:
                this.dataManager.addDataCollector(this.sensorFactory.make(SensorType.TEMPERATURE));
                break;
            case ADD_WIND_SPEED_SENSOR:
                this.dataManager.addDataCollector(this.sensorFactory.make(SensorType.WIND_SPEED));
                break;
            case COLLECT:
                this.dataManager.collectAndStoreData();
                break;
            case DISPLAY:
                this.userInterface.displayDashboard();
                break;
            case DISPLAY_ALL:
                this.userInterface.displayAllData(this.dataManager.getAllData());
                break;
            case REPORT_TEXT:
                this.reportGenerator.generate(ReportType.TEXT, this.dataManager.getAllData());
                break;
            case REPORT_CSV:
                this.reportGenerator.generate(ReportType.CSV, this.dataManager.getAllData());
                break;
            case EXIT:
                this.userInterface.stop();
                break;
            case HELP:
                this.userInterface.displayHelp();
                break;
        }
    }

    @Override
    public IDataManager getDataManager() {
        return this.dataManager;
    }

    public void setDataManager(final IDataManager dataManager) {
        this.dataManager = dataManager;
    }
}
