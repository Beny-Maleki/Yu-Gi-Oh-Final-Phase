package connector.cards;

import client.controller.gamecontrollers.GetStringInputFromView;
import client.model.cards.cardsActions.Action;
import client.model.cards.cardsActions.magicActionChildren.*;
import client.model.cards.cardsEnum.Magic.MagicAttribute;
import client.model.cards.cardsEnum.Magic.MagicType;
import client.model.cards.cardsEnum.Magic.RestrictionTypeInAdding;
import client.model.cards.cardsEnum.Monster.MonsterRace;
import client.model.enums.GameEnums.RequestingInput;
import client.model.enums.GameEnums.SideOfFeature;
import client.model.events.Event;
import client.model.events.eventChildren.ActivationInOpponentTurn;
import client.model.events.eventChildren.ManuallyActivation;
import client.model.events.eventChildren.MonsterSummon;
import client.model.events.eventChildren.OpponentMonsterWantsToAttack;
import client.model.gameprop.gamemodel.Game;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;


public class MagicCard extends Card {

    private ArrayList<Action> actionsOfMagic;
    private ArrayList<Event> triggers;
    private RestrictionTypeInAdding restrictionTypeInAdding;
    private MagicAttribute magicAttribute;
    private MagicType typeOfMagic;
    private boolean isActivated;

    {
        isActivated = false;
        actionsOfMagic = new ArrayList<>();
        triggers = new ArrayList<>();
    }

    public MagicCard(String name, String typeOfMagic, String magicAttribute, String description, String typeOfRestriction, String price) {
        super(name, description, price);
        setTypeOfMagic(MagicType.setType(typeOfMagic));
        setMagicAttribute(MagicAttribute.setAttribute(magicAttribute));
        setMagicRestriction(RestrictionTypeInAdding.setSpeed(typeOfRestriction));
        setMagicEffect(name);
        setMagicEvents(name);
    }

    public MagicCard() {
        super();
    }
    @Override
    public Card getSimilarCard() {
        return new MagicCard(name, typeOfMagic.toString(), magicAttribute.toString(),
                description, restrictionTypeInAdding.toString(), String.valueOf(price));
    }

    public void setActionsOfMagic(ArrayList<Action> actionsOfMagic) {
        this.actionsOfMagic = actionsOfMagic;
    }

    public void setTriggers(ArrayList<Event> triggers) {
        this.triggers = triggers;
    }

    private void setMagicEvents(String name) {
        switch (name) {
            case "Forest":
            case "Closed Forest":
            case "Monster Reborn":
            case "Umiiruka":
            case "Rage":
            case "Harpie’s Feather Duster":
            case "Dark Hole":
            case "Mystical space typhoon":
            case "Yami":
            case "Sword of Dark Destruction":
            case "Black Pendant":
            case "Mind Crush":
            case "Call of the Haunted":
            case "Change of Heart":
            case "Time Seal":
            case "Pot Of Greed":
            case "Terraforming":
                triggers.add(ManuallyActivation.getInstance());
                break;
            case "Mirror Force":
                triggers.add(OpponentMonsterWantsToAttack.getInstance());
                break;
            case "Torrential Tribute":
                triggers.add(MonsterSummon.getInstance());
                break;
        }
    }

    private void setMagicEffect(String name) {
        if (name.equals("Monster Reborn")) {
            actionsOfMagic.add(new SummonMonsterFromBothGraveYardsAction());
        }
        if (name.equals("Raigeki")) {
            actionsOfMagic.add(new DestroyAllOpponentMonsters());
        }
        if (name.equals("Harpie’s Feather Duster")) {
            actionsOfMagic.add(new DestroyAllOpponentMagics());
        }
        if (name.equals("Dark Hole")) {
            actionsOfMagic.add(new DestroyAllBoardMonsters());
        }
        if (name.equals("Mystical space typhoon")) {
            actionsOfMagic.add(new DestroyAMagicCard());
        }
        if (name.equals("Yami")) {
            ArrayList<String> types = new ArrayList<>();
            types.add(MonsterRace.FIEND.toString());
            types.add(MonsterRace.SPELLCASTER.toString());
            ArrayList<SideOfFeature> sideOfFeatures = new ArrayList<>();
            sideOfFeatures.add(SideOfFeature.CURRENT);
            sideOfFeatures.add(SideOfFeature.OPPONENT);
            actionsOfMagic.add(new ChangingMonsterAttackAction(200, types, 1, sideOfFeatures));
            actionsOfMagic.add(new ChangingMonsterDefenceAction(200, types, 1, sideOfFeatures));
            types = new ArrayList<>();
            types.add(MonsterRace.FAIRY.toString());
            actionsOfMagic.add(new ChangingMonsterAttackAction(200, types, -1, sideOfFeatures));
            actionsOfMagic.add(new ChangingMonsterDefenceAction(200, types, -1, sideOfFeatures));
        }
        if (name.equals("Forest")) {
            ArrayList<String> types = new ArrayList<>();
            types.add(MonsterRace.BEAST.toString());
            types.add(MonsterRace.INSECT.toString());
            types.add(MonsterRace.BEAST_WARRIOR.toString());
            ArrayList<SideOfFeature> sideOfFeatures = new ArrayList<>();
            sideOfFeatures.add(SideOfFeature.CURRENT);
            sideOfFeatures.add(SideOfFeature.OPPONENT);
            actionsOfMagic.add(new ChangingMonsterAttackAction(200, types, 1, sideOfFeatures));
            actionsOfMagic.add(new ChangingMonsterDefenceAction(200, types, 1, sideOfFeatures));
        }
        if (name.equals("Closed Forest")) {
            ArrayList<String> types = new ArrayList<>();
            types.add(MonsterRace.BEAST.toString());
            types.add(MonsterRace.BEAST_WARRIOR.toString());
            ArrayList<SideOfFeature> sideOfFeaturesGraveyard = new ArrayList<>();
            sideOfFeaturesGraveyard.add(SideOfFeature.OPPONENT);
            sideOfFeaturesGraveyard.add(SideOfFeature.CURRENT);
            ArrayList<SideOfFeature> sideOfFeaturesChangeAttack = new ArrayList<>();
            sideOfFeaturesChangeAttack.add(SideOfFeature.CURRENT);
            actionsOfMagic.add(new ChangingMonsterAttackWithGraveyardsMonster(100, 1, types, sideOfFeaturesGraveyard, sideOfFeaturesChangeAttack));
        }
        if (name.equals("Umiiruka")) {
            ArrayList<String> types = new ArrayList<>();
            types.add(MonsterRace.AQUA.toString());
            ArrayList<SideOfFeature> sideOfFeatures = new ArrayList<>();
            sideOfFeatures.add(SideOfFeature.CURRENT);
            sideOfFeatures.add(SideOfFeature.OPPONENT);
            actionsOfMagic.add(new ChangingMonsterAttackAction(500, types, 1, sideOfFeatures));
            actionsOfMagic.add(new ChangingMonsterDefenceAction(400, types, -1, sideOfFeatures));
        }
        if (name.equals("Sword of Dark Destruction")) {
            ArrayList<String> types = new ArrayList<>();
            types.add(MonsterRace.FIEND.toString());
            types.add(MonsterRace.SPELLCASTER.toString());
            actionsOfMagic.add(new ChangingSomeRaceEquipedMonsterAttack(400, 1, types));
            actionsOfMagic.add(new ChangingSomeRaceEquipedMonsterDefence(200, -1, types));
        }
        if (name.equals("Black Pendant")) {
            actionsOfMagic.add(new ChangingEquipedMonsterAttack(500, 1));
        }
        if (name.equals("Mirror Force")) {
            actionsOfMagic.add(new DestroyAllOpponentAttackingMonsters());
        }
        if (name.equals("Mind Crush")) {
            actionsOfMagic.add(new GuessingCardInOpponentHandAction());
        }
        if (name.equals("Torrential Tribute")) {
            actionsOfMagic.add(new DestroyAllBoardMonsters());
        }
        if (name.equals("Call of the Haunted")) {
            actionsOfMagic.add(new SummonMonsterFromOwnGraveYardAction());
        }
        if (name.equals("Change of Heart")) {
            actionsOfMagic.add(new ChangeTeamOfMonsterCard());
        }
        if (name.equals("Time Seal")) {
            actionsOfMagic.add(new AvoidOpponentsCardDraw(2));
        }
        if (name.equals("Pot Of Greed")) {
            actionsOfMagic.add(new DrawCardFromTopOfDeck(2));
        }
        if (name.equals("Terraforming")) {
            actionsOfMagic.add(new TerraformingAction());
        }
    }

    @Override
    public void activeEffectsByEvent(Event event, Game game) throws FileNotFoundException {
        boolean shouldActiveEffects = false;
        for (Event trigger : triggers) {
            if (eventEquals(trigger, event)) {
                shouldActiveEffects = true;
                break;
            }
        }

        if (event instanceof ActivationInOpponentTurn) {
            String answer = GetStringInputFromView.getInputFromView(RequestingInput.DOES_PLAYER_WANT_TO_ACTIVE_SPELL, this.name);
            while (true) {
                switch (answer.toLowerCase(Locale.ROOT)) {
                    case "yes": {
                        isActivated = true;
                        activeActions(game, shouldActiveEffects);
                        return;
                    }
                    case "no": {
                        return;
                    }
                    default: {
                        answer = GetStringInputFromView.getInputFromView(RequestingInput.INVALID_ANSWER);
                    }
                }
            }

        }
        isActivated = true;
        activeActions(game, shouldActiveEffects);
    }

    private void activeActions(Game game, boolean shouldActiveEffects) throws FileNotFoundException {
        if (shouldActiveEffects) {
            for (Action actionOfMagic : actionsOfMagic) {
                actionOfMagic.active(game);
            }
        }
    }

    private boolean eventEquals(Event firstEvent, Event secondEvent) {
        if (firstEvent instanceof ActivationInOpponentTurn && secondEvent instanceof ActivationInOpponentTurn)
            return true;
        if (firstEvent instanceof ManuallyActivation && secondEvent instanceof ManuallyActivation)
            return true;
        if (firstEvent instanceof OpponentMonsterWantsToAttack && secondEvent instanceof OpponentMonsterWantsToAttack)
            return true;
        return firstEvent instanceof MonsterSummon && secondEvent instanceof MonsterSummon;
    }

    @Override
    public String getCardDetail() {
        return
                "Name: " + name +
                        "\n" + typeOfMagic.toString() +
                        "\nType: " + magicAttribute +
                        "\nSpeed: " + restrictionTypeInAdding.toString() +
                        "\nDescription: " + description;
    }

    @Override
    public String getCardDetailWithEnters() {
        return
                "Name: " + name +
                        "\n" + typeOfMagic.toString() +
                        "\nType: " + magicAttribute +
                        "\nSpeed: " + restrictionTypeInAdding.toString() +
                        "\nDescription:\n" + getCardDescriptionWithEnters();
    }


    public MagicAttribute getMagicAttribute() {
        return magicAttribute;
    }

    private void setMagicAttribute(MagicAttribute magicAttribute) {
        this.magicAttribute = magicAttribute;
    }

    public ArrayList<Event> getTriggers() {
        return triggers;
    }

    public MagicType getTypeOfMagic() {
        return typeOfMagic;
    }

    private void setTypeOfMagic(MagicType typeOfMagic) {
        this.typeOfMagic = typeOfMagic;
    }

    public RestrictionTypeInAdding getMagicRestrictionType() {
        return restrictionTypeInAdding;
    }

    public void setMagicRestriction(RestrictionTypeInAdding restrictionTypeInAdding) {
        this.restrictionTypeInAdding = restrictionTypeInAdding;
    }

    public void setDetails(String name, String typeOfMagic, String magicAttribute, String description, String speed, String price) {
        setName(name);
        setTypeOfMagic(MagicType.setType(typeOfMagic));
        setMagicAttribute(MagicAttribute.setAttribute(magicAttribute));
        setMagicRestriction(RestrictionTypeInAdding.setSpeed(speed));
        setDescription(description);
        setPrice(price);
        setMagicEffect(name);
        setMagicEvents(name);
    }

    public ArrayList<Action> getActionsOfMagic() {
        return actionsOfMagic;
    }

    @Override
    public Card getCopy() {
        MagicCard copy = new MagicCard();

        // semi duplicate block:
        copy.name = this.name;
        copy.description = this.description;
        copy.price = this.price;
        copy.ID = this.ID;
        //

        copy.setMagicRestriction(this.restrictionTypeInAdding);
        copy.setTypeOfMagic(this.typeOfMagic);
        copy.setMagicAttribute(this.magicAttribute);

        return copy;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }
}
