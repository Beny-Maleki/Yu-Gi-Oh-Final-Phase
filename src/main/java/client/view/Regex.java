package client.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Regex {
    public static String menuEnter = "client.controller enter (.+)";
    public static String profileChange = "profile change (.*)";
    public static String changeNickname = "profile change --nickname (.*)";
    public static String changePassword = ".+(?<= |^)--password(?= --current.*| --new.*|$).*$";
    public static String currentPassword = ".+(?<= |^)--current (\\S+)(?= --password| --new.*|$).*$";
    public static String newPassword = ".+(?<= |^)--new (\\S+)(?= --password| --current.*|$).*$";
    public static String duel = "duel (.+)";
    public static String showCard = "card show (?<cardName>.+)";

    //Register Menu connector.commands
    // -> create user
    public static String[] userCreateCommand = new String[]{
            "user create --username .+ --nickname .+ --password .+",
            "user create --username .+ --password .+ --nickname .+",
            "user create --nickname .+ --username .+ --password .+",
            "user create --nickname .+ --password .+ --username .+",
            "user create --password .+ --nickname .+ --username .+",
            "user create --password .+ --username .+ --nickname .+"
    };
    // -> login user
    public static String[] userLoginCommand = new String[]{
            "user login --username .+ --password .+",
            "user login --password .+ --username .+"
    };

    // -> main client.controller connector.commands
    public static String moneyCheat = "cheat code: increase money -> (?<amount>\\d+)";

    // -> fixed connector.commands
    public static String[] otherCommands = new String[]{
            "user logout",
            "client.controller exit",
            "client.controller show-current",
            "client.controller enter.+"
};

    // -> all connector.commands
    public static String[][] registerCommands = new String[][]{
            userCreateCommand,
            userLoginCommand,
            otherCommands
    };


    //DuelPageView connector.commands:
    public static String[] duelMenuCommands = new String[]{
            ".+(?<= |^)--second-player (\\S+)(?= --new| --rounds.*|$).*$",
            ".+(?<= |^)--rounds (\\S+)(?= --new| --second-player.*|$).*$",
            ".+(?<= |^)--new(?= --second-player.*| --rounds.*|$).*$",
            "client.controller show-current"
    };
    // DeckMenu Commands:
    public static String createDeck = "deck create (?<deckName>.+)";
    public static String deleteDeck = "deck delete (?<deckName>.+)";
    public static String activateDeck = "deck set-active (?<deckName>.+)";

    // adding card connector.commands:
    public static String[] addCardToMainDeck = new String[]{
            "^deck add-card --card (?<cardName>.+) --deck (?<deckName>.+)$",
            "^deck add-card --deck (?<deckName>.+) --card (?<cardName>.+)$"
    };
    public static String[] addCardToSideDeck = new String[]{
            // --side at end:
            "^deck add-card --card (?<cardName>.+) --deck (?<deckName>.+?) --side$",
            "^deck add-card --deck (?<deckName>.+) --card (?<cardName>.+?) --side$",
            // --side at middle:
            "^deck add-card --deck (?<deckName>.+?) --side --card (?<cardName>.+)$",
            "^deck add-card --card (?<cardName>.+?) --side --deck (?<deckName>.+)$",
            // --side at first:
            "^deck add-card --side --deck (?<deckName>.+) --card (?<cardName>.+)$",
            "^deck add-card --side --card (?<cardName>.+) --deck (?<deckName>.+)$"
    };

    // removing cards from deck connector.commands:
    public static String[] removeCardFromMainDeck = new String[]{
            "^deck rm-card --card (?<cardName>.+) --deck (?<deckName>.+)$",
            "^deck rm-card --deck (?<deckName>.+) --card (?<cardName>.+)$",
    };
    public static String[] removeCardFromSideDeck = new String[]{
            // --side at end:
            "^deck rm-card --card (?<cardName>.+) --deck (?<deckName>.+?) --side$",
            "^deck rm-card --deck (?<deckName>.+) --card (?<cardName>.+?) --side$",
            // --side at middle:
            "^deck rm-card --deck (?<deckName>.+?) --side --card (?<cardName>.+)$",
            "^deck rm-card --card (?<cardName>.+?) --side --deck (?<deckName>.+)$",
            // --side at first:
            "^deck rm-card --side --deck (?<deckName>.+) --card (?<cardName>.+)$",
            "^deck rm-card --side --card (?<cardName>.+) --deck (?<deckName>.+)$"
    };
    // show Deck (main/side):
    public static String showOneMainDeck = "^deck show --deck-name (?<deckName>.+)$";
    public static String[] showOneSideDeck = new String[]{
            "^deck show --deck-name (?<deckName>.+?) --side$",
            "^deck show --side --deck-name (?<deckName>.+)$"
    };

    public static String showAllCardsDeckMenu = "^deck show --cards$";
    // <- end of DeckMenu connector.commands;


    //ShopMenu connector.commands:
    public static String buyCard = "^shop buy (?<cardName>.+)";
    public static String showAllShop = "shop show --all";
    // <- end of ShopMenu connector.commands;

    //GamePlay -> general connector.commands
    public static String[] generalCommands = new String[]{
            "cheat code: winner",
            "cheat code: draw",
            "cheat code: increase LP -> (?<amount>\\d{1,})",
            "surrender",
            "active effect",
            "card show --selected",
            "show graveyard(?= --opponent|$)",
            "show graveyard --opponent",
            "select --monster (?<address>\\d+)",
            "select --spell (?<address>\\d+)",
            "select --field (?<address>\\d+)",
            "select --opponent --monster (?<address>\\d+)",
            "select --opponent --spell (?<address>\\d+)",
            "select --opponent --field (?<address>\\d+)",
            "select --hand (?<address>\\d+)",
            "select -d",
            "next phase",
            "draw board",
            "START"
    };
    //GamePlay -> side pages command
    public static String[] sideStageCommand = new String[]{
            "T:\\d$",
            "T1:\\d T2:\\d$",
            "Finish",
            "Show client.Main Deck",
            "Switch (\\d+) -> (\\d+)"
    };

    //GamePlay -> draw phaseCommands -> nothing special
    //GamePlay -> main phaseCommands
    public static String[] mainPhaseCommands = new String[]{
            "summon",
            "set",
            "^set --position attack",
            "^set --position defence",
            "^flip-summon",
    };
    //GamePlay -> battle  phaseCommands
    public static String[] battlePhaseCommands = new String[]{
            "attack \\d",
            "attack direct"
    };

    public static String[][] allGamePlayCommands = new String[][]{
            mainPhaseCommands,
            battlePhaseCommands
    };


    public static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

}
