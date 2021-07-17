package connector.cards;

import client.model.cards.cardsEnum.Monster.MonsterAttribute;
import client.model.cards.cardsEnum.Monster.MonsterRace;
import client.model.cards.cardsEnum.Monster.MonsterType;

public class MonsterCard extends Card {

    protected int attack;
    protected int defence;
    protected int level;
    protected MonsterType type;
    protected MonsterRace race;
    protected MonsterAttribute attribute;
    protected MagicCard equippedWith;

    public MonsterCard(String name, String level, String attribute, String race, String type,
                       String attack, String defence, String description, String price) {
        super(name, description, price);
        setAttack(Integer.parseInt(attack));
        setDefence(Integer.parseInt(defence));
        setLevel(Integer.parseInt(level));
        setType(MonsterType.assignType(type));
        setRace(MonsterRace.assignRace(race));
        setAttribute(MonsterAttribute.assignAttribute(attribute));
    }

    public MonsterCard() {
    }


    @Override
    public Card getSimilarCard() {
            return new MonsterCard(name, String.valueOf(level), attribute.toString(), race.toString(), type.toString(),
                    String.valueOf(attack), String.valueOf(defence), description, String.valueOf(price));
    }

    public void setDetails(String name, String level, String attribute, String race, String type,
                           String attack, String defence, String description, String price) {
        setName(name);
        setDescription(description);
        setPrice(price);
        setAttack(Integer.parseInt(attack));
        setDefence(Integer.parseInt(defence));
        setLevel(Integer.parseInt(level));
        setType(MonsterType.assignType(type));
        setRace(MonsterRace.assignRace(race));
        setAttribute(MonsterAttribute.assignAttribute(attribute));
    }

    @Override
    public String getCardDetail() {
        return "Name: " + name +
                "\nLevel: " + level +
                "\nType: " + race.toString() +
                "\nATK: " + attack +
                "\nDEF: " + defence +
                "\nDescription: " + description;

    }

    @Override
    public String getCardDetailWithEnters() {
        return
                "Name: " + name +
                        "\nLevel: " + level +
                        "\nType: " + race.toString() +
                        "\nATK: " + attack +
                        "\nDEF: " + defence +
                        "\nDescription:\n" + getCardDescriptionWithEnters();
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public MonsterType getType() {
        return type;
    }

    public void setType(MonsterType type) {
        this.type = type;
    }

    public MonsterRace getRace() {
        return race;
    }

    public void setRace(MonsterRace race) {
        this.race = race;
    }

    public MonsterAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(MonsterAttribute attribute) {
        this.attribute = attribute;
    }

    public MagicCard getEquippedWith() {
        return equippedWith;
    }

    public void setEquippedWith(MagicCard equippedWith) {
        this.equippedWith = equippedWith;
    }

    @Override
    public Card getCopy() { // "Prototype pattern" wasn't approachable!
        // cause -> class fields' types didn't match to constructor types!
        MonsterCard copy = new MonsterCard();

        // semi duplicate block:
        copy.name = this.name;
        copy.description = this.description;
        copy.price = this.price;
        copy.ID = this.ID;
        //

        copy.setAttack(this.attack);
        copy.setDefence(this.defence);
        copy.setType(this.type);
        copy.setAttribute(this.attribute);
        copy.setLevel(this.level);
        copy.setRace(this.race);

        return copy;
    }
}
