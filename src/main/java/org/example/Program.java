package org.example;

import org.apache.commons.lang3.StringUtils;

public class Program {
    public static void main(String[] args) throws InterruptedException {

        Machine machine = new Machine();
        machine.loadStats("./src/main/resources/stats.json");
        Stats stats = machine.getStats();
        Integer coins = stats.getCoins();

        System.out.println("°º¤ø,¸¸,ø¤º°`°º¤ø,¸,ø¤°º¤ø,¸¸,ø¤º°`");
        System.out.println("Bienvenue au Casino de Céladopole !");
        System.out.println("°º¤ø,¸¸,ø¤º°`°º¤ø,¸,ø¤°º¤ø,¸¸,ø¤º°`");
        System.out.println();

        machine.print();
        System.out.println();

        //Demander le nombre de jetons a miser
        while (coins > 0) {

            String askJetons = "0";
            while (!askJetons.matches("[1-3]|exit")) {
                askJetons = machine.ask("Combien de jetons ? (1, 2 ou 3) : ").replace(" ", "");
            }

            if(StringUtils.equals(askJetons,"exit")) {
                System.out.println();
                System.out.println("°º¤ø,¸¸,ø¤º°`°º¤ø,¸,ø¤°º¤ø,¸¸,ø¤º°`");
                System.out.println("A Bientot au Casino de Céladopole !");
                System.out.println("°º¤ø,¸¸,ø¤º°`°º¤ø,¸,ø¤°º¤ø,¸¸,ø¤º°`");
                System.exit(0);
            }

            System.out.println("---------------------------------");

            Integer nbJetons = Integer.parseInt(askJetons);
            stats.setCoinsSpent(stats.getCoinsSpent()+nbJetons);
            if (coins - nbJetons > 0) {

                machine.roll();
                System.out.println();
                machine.print();
                System.out.println();

                Integer gains = 0;
                switch (nbJetons) {
                    case 1:
                        gains += machine.checkMiddleLine();
                        break;
                    case 2:
                        gains += machine.checkAllLines();
                        break;
                    case 3:
                        gains += machine.checkLinesAndDiags();
                        break;
                }

                //STATS
                stats.setCoinsSpent( stats.getCoinsSpent() + nbJetons);
                stats.setGamesPlayed(stats.getGamesPlayed()+1);
                stats.setCoins(coins + gains);
                if(gains > 0){
                    System.out.println("Bravo ! Tu as gagné des coins !");
                    stats.setGamesWon(stats.getGamesWon()+1);
                }

                machine.setStats(stats);
                machine.saveStats("./src/main/resources/stats.json");

                //affichage de fin de manche
                System.out.println();
                System.out.println("Jeton.s misé.s : " + nbJetons);
                System.out.println("Gains : " + gains);
                System.out.println("Jeton.s possédé.s : " + coins);


            } else System.out.println("Vous n'avez pas assez de jeton pour cette somme");

        }

    }

}