/*
 * Author: Victor Emanuel Farias da Costa Borges
 * Contact: victor.borges@embedded.ufcg.edu.br
 */

import org.eclipse.mosaic.fed.application.app.AbstractApplication;
import org.eclipse.mosaic.fed.application.app.api.VehicleApplication;
import org.eclipse.mosaic.fed.application.app.api.os.VehicleOperatingSystem;
import org.eclipse.mosaic.lib.objects.vehicle.VehicleData;
import org.eclipse.mosaic.lib.util.scheduling.Event;

/**
 * RepConfigurationApp
 * <p>
 * O objetivo dessa aplicação é criar e manter uma wallet de dados para cada instância de veículo na simulação.
 */

public class RepConfigurationApp extends AbstractApplication<VehicleOperatingSystem> implements VehicleApplication {

    @Override
    public void onStartup() {
        getLog().info("Hello World!");
    }

    @Override
    public void onVehicleUpdated(VehicleData previousVehicleData, VehicleData updatedVehicleData) {
        getLog().info("Driving {} m/s.", updatedVehicleData.getSpeed());
    }

    @Override
    public void onShutdown() {
        getLog().info("Good bye!");
    }

    @Override
    public void processEvent(Event event) {
        // ...
    }

    // Método para verificar valor do intervalo
    public boolean verifySw(double t_v, double timestamp) {
        double inf = t_v - timestamp;
        double sup = t_v + timestamp;
        return (inf <= t_v && t_v <= sup);
    }

    // Método para calcular fpor
    public double calculateFpor(double tpor, double dpor) {
        return (tpor + dpor) / 2;
    }

    // Método para calcular tpor
    public double calculateTpor(double t_approx, double t_current) {
        if (t_current != 0) {
            return t_approx / t_current;
        } else {
            return 0;
        }
    }

    // Método para calcular dpor
    public double calculateDpor(double[] d_approx, double[] d_ran) {
        // latitude [0] e longitude [1]
        return (d_approx[0] + d_approx[1]) / (d_ran[0] + d_ran[1]);
    }

    // Método para calcular o índice de confiança (ic)
    public double calculateIc(double[] c_s, double[] fpor_s) {
        double sum = 0;
        for (int i = 0; i < fpor_s.length; i++) {
            sum += (c_s[i] + fpor_s[i]);
        }
        return sum / fpor_s.length;
    }

    // Método para retornar confiança com base no índice de confiança (ic)
    public boolean returnTrustIc(double ic) {
        return ic >= 0.5;
    }

    // Método para retornar confiança com base na reputação (rep)
    public boolean returnTrustRep(double rep) {
        return rep >= 3.5;
    }

    public double proofOfReputation() {

    }
}