/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgrite.project.techlaurent;

import java.io.IOException;

/**
 *
 * @author fotso
 */
public class TestPerform {

    public static void main(String[] args) throws IOException {
        for (int i = 1; i < 3; i++) {
            Support s = new Support(i);
            s.dataForDrawGraphe(s.getOutSupport1(), s.getOutSupport2());
        }

    }

}
