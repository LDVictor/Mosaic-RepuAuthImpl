/*
 * Author: Victor Emanuel Farias da Costa Borges
 * Contact: victor.borges@embedded.ufcg.edu.br
 */

package org.eclipse.mosaic.app.tutorial.configurableapp;

import java.util.ArrayList;

/**
 * Classe que representa o veiculo com implementação do RepuAuth-IoV
 */
public class RepuAuthVehicle {
    public String credential;
    public double reputation;
    public ArrayList<String> pendingMsgList = new ArrayList<>();
    public ArrayList<String> pendingCredList = new ArrayList<>();
}
