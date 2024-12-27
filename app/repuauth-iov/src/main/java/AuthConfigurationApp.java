/*
 * Author: Victor Emanuel Farias da Costa Borges
 * Contact: victor.borges@embedded.ufcg.edu.br
 */

package org.eclipse.mosaic.app.tutorial.configurableapp;

import org.eclipse.mosaic.fed.application.app.ConfigurableApplication;
import org.eclipse.mosaic.fed.application.app.api.os.VehicleOperatingSystem;
import org.eclipse.mosaic.lib.util.scheduling.Event;
import org.eclipse.mosaic.rti.TIME;

/**
 * This is a simple application to demonstrate a configurable application.
 * <p>
 * A configuration file(s) should be placed in "application" folder.
 * The filename can end with "_unitId" (e.g. VehicleConfigurationApp_veh_1.json) and will then only be used by the specified unit.
 * It allows configuring one application in different ways for different vehicles.
 * If the configuration filename doesn't include any unit id, it will be used for all unspecified units.
 */

/**
 * AuthConfigurationApp
 * <p>
 * O objetivo dessa aplicação é criar e manter uma wallet de dados para cada instância de veículo na simulação.
 */
public class AuthConfigurationApp extends ConfigurableApplication<RepuAuthVehicle, VehicleOperatingSystem> {

    /**
     * Data wallet.
     */
    private RepuAuthVehicle wallet;

    public VehicleConfigurationApp() {
        super(RepuAuthVehicle.class, "AuthConfigurationApp");
    }

    @Override
    public void onStartup() {
        this.config = this.getConfiguration();

         // Fazendo a requisição de credencial verificável ao Hyperledger Identus Cloud Agent
         VerifiableCredential credential = HyperledgerClient.requestVerifiableCredential(wallet.getIdentifier());
         wallet.setCredential(credential);

         this.getOs().getEventManager()
         .newEvent(getOs().getSimulationTime() + 3 * TIME.SECOND, this)
         .withResource("Vehicle with AuthConfigurationApp (credential " + wallet.credential + ") is created created at the time " + getOs().getSimulationTime())
         .schedule();
         }
    }

    @Override
    public void onVehicleUpdated(VehicleData previousVehicleData, VehicleData updatedVehicleData) {
        getLog().info("Driving {} m/s.", updatedVehicleData.getSpeed());
    }

    @Override
    public void onShutdown() {
        getLog().info("Vehicle with AuthConfigurationApp (credential " + wallet.credential + ") shutdown.");

    }

    @Override
    public void processEvent(Event event) {
        // ...
    }

}