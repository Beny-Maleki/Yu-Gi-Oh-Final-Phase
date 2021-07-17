package connector.commands.commnadclasses;

import connector.cards.MagicCard;
import connector.cards.MonsterCard;
import connector.commands.Command;
import connector.commands.CommandType;

import java.util.List;

public class GetUsersCardCommand extends Command {
    private List<MonsterCard> userMonsterCard;
    private List<MagicCard> userMagicCard;


    public GetUsersCardCommand(CommandType commandType, String token) {
        super(commandType);
        this.token = token;
    }

    public List<MagicCard> getUserMagicCard() {
        return userMagicCard;
    }

    public void setUserMagicCard(List<MagicCard> userMagicCard) {
        this.userMagicCard = userMagicCard;
    }

    public List<MonsterCard> getUserMonsterCard() {
        return userMonsterCard;
    }

    public void setUserMonsterCard(List<MonsterCard> userMonsterCard) {
        this.userMonsterCard = userMonsterCard;
    }
}
