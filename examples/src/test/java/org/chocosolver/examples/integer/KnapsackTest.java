/*
 * This file is part of examples, http://choco-solver.org/
 *
 * Copyright (c) 2020, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 *
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.examples.integer;

import org.chocosolver.parser.SetUpException;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 19 juil. 2010
 */
public class KnapsackTest {

    public Model modelIt(String data, int n) throws SetUpException {
        Knapsack pb = new Knapsack();
        pb.setUp("-d", data, "-n", "" + n);
        pb.buildModel();
        for (IntVar v : pb.objects) {
            if (v == null) {
                throw new UnsupportedOperationException();
            }
        }
        return pb.getModel();
    }

    public void solveIt(Model s, boolean optimize) {
        if (optimize) {
            // BEWARE trick to find power variable
            IntVar power = null;
            for (int i = s.getNbVars() - 1; i >= 0; i--) {
                if (s.getVar(i).getName().equals("power")) {
                    if (power != null) {
                        throw new UnsupportedOperationException("The solver has more than one power variable");
                    }
                    power = (IntVar) s.getVar(i);
                }
            }
            if (power == null) {
                throw new UnsupportedOperationException("The solver has no power variable");
            }
            // end of trick
            s.setObjective(true, power);
        }
        while(s.getSolver().solve());
    }

    @Test(groups="1s", timeOut=60000)
    public void testMain() throws SetUpException {
        KnapsackTest ks = new KnapsackTest();
        ks.solveIt(ks.modelIt("k10", 10), true);
        ks.solveIt(ks.modelIt("k20", 13), true);
    }


	@Test(groups="1s", timeOut=60000)
    public void testALL0() throws SetUpException {
       KnapsackTest ks = new KnapsackTest();
        for (int i = 0; i < 1; i++) {
            Model s = ks.modelIt("k0", 10);
            ks.solveIt(s, true);
            Assert.assertEquals(s.getSolver().getBestSolutionValue().intValue(), 7513, "obj val");
        }
    }

    @Test(groups="1s", timeOut=60000)
    public void testALL5() throws SetUpException {
        KnapsackTest ks = new KnapsackTest();
        for (int i = 0; i < 1; i++) {
            Model s = ks.modelIt("k10", 3);
            ks.solveIt(s, true);
            Assert.assertEquals(s.getSolver().getBestSolutionValue().intValue(), 1078, "obj val");
            Assert.assertEquals(s.getSolver().getSolutionCount(), 1, "nb sol");
//            Assert.assertEquals(s.getResolver().getMeasures().getNodeCount(), 7, "nb nod");
        }
    }

    @Test(groups="1s", timeOut=60000)
    public void testALL10() throws SetUpException {
        KnapsackTest ks = new KnapsackTest();
        for (int i = 0; i < 1; i++) {
            Model s = ks.modelIt("k10", 10);
            ks.solveIt(s, true);
            Assert.assertEquals(s.getSolver().getBestSolutionValue().intValue(), 1078, "obj val");
            Assert.assertEquals(s.getSolver().getSolutionCount(), 1, "nb sol");
            Assert.assertEquals(s.getSolver().getNodeCount(), 11, "nb nod");
        }
    }

    @Test(groups="1s", timeOut=60000)
    public void testOPT13() throws SetUpException {
        KnapsackTest ks = new KnapsackTest();
        Model s = ks.modelIt("k20", 13);
        ks.solveIt(s, true);
        Assert.assertEquals(s.getSolver().getBestSolutionValue().intValue(), 2657, "obj val");
        Assert.assertEquals(s.getSolver().getSolutionCount(), 2, "nb sol");
        Assert.assertEquals(s.getSolver().getNodeCount(), 15, "nb nod");
    }

    @Test(groups="1s", timeOut=60000)
    public void testOPT14() throws SetUpException {
        KnapsackTest ks = new KnapsackTest();
        Model s = ks.modelIt("k20", 14);
        ks.solveIt(s, true);
        Assert.assertEquals(s.getSolver().getBestSolutionValue().intValue(), 2657, "obj val");
        Assert.assertEquals(s.getSolver().getSolutionCount(), 2, "nb sol");
        Assert.assertEquals(s.getSolver().getNodeCount(), 16, "nb nod");
    }

    @Test(groups="1s", timeOut=60000)
    public void testOPT15() throws SetUpException {
        KnapsackTest ks = new KnapsackTest();
        Model s = ks.modelIt("k20", 15);
        ks.solveIt(s, true);
        Assert.assertEquals(s.getSolver().getBestSolutionValue().intValue(), 2657, "obj val");
        Assert.assertEquals(s.getSolver().getSolutionCount(), 2, "nb sol");
        Assert.assertEquals(s.getSolver().getNodeCount(), 17, "nb nod");
    }

}
