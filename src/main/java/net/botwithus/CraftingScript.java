package net.botwithus;

import net.botwithus.api.game.hud.inventories.Backpack;
import net.botwithus.api.game.hud.inventories.Bank;
import net.botwithus.api.game.hud.traversal.Lodestone;
import net.botwithus.internal.scripts.ScriptDefinition;
import net.botwithus.rs3.events.impl.InventoryUpdateEvent;
import net.botwithus.rs3.game.Area;
import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.game.Coordinate;
import net.botwithus.rs3.game.actionbar.ActionBar;
import net.botwithus.rs3.game.hud.interfaces.Interfaces;
import net.botwithus.rs3.game.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.game.queries.results.ResultSet;
import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;
import net.botwithus.rs3.game.scene.entities.object.SceneObject;
import net.botwithus.rs3.game.skills.Skills;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.script.LoopingScript;
import net.botwithus.rs3.script.config.ScriptConfig;
import net.botwithus.rs3.game.*;
import net.botwithus.rs3.util.Regex;


import java.time.Instant;
import java.util.Random;
import java.util.regex.Pattern;


public class CraftingScript extends LoopingScript {

    private BotState botState = BotState.IDLE;
    private boolean someBool = true;
    private boolean someBool1 = true;
    private Random random = new Random();

    public long scriptStartTime = System.currentTimeMillis();

    public int GemMined  = 0;
    public int GemMinedPerHour = 0;

    private Area AlKharid = new Area.Rectangular(new Coordinate(3274,3168,0), new Coordinate(3267,3171,0));
    private Area AlKharid1 = new Area.Rectangular(new Coordinate(3301,3284,0), new Coordinate(3306,3274,0));
    private Area AlKharid2 = new Area.Rectangular(new Coordinate(3301,3241,0), new Coordinate(3306,3231,0));
    private Area AlKharid3 = new Area.Rectangular(new Coordinate(3301,3206,0), new Coordinate(3304,3197,0));
    private Area AlKharid4 = new Area.Rectangular(new Coordinate(3289,3181,0), new Coordinate(3295,3181,0));

    private Pattern Gems = Regex.getPatternForContainingOneOf("Uncut ruby", "Uncut sapphire", "Uncut emerald");


    enum BotState {
        //define your own states here
        IDLE,
        SKILLING,
        BANKING,
        //...

    }

    public CraftingScript(String s, ScriptConfig scriptConfig, ScriptDefinition scriptDefinition) {
        super(s, scriptConfig, scriptDefinition);
        this.sgc = new CraftingScriptGraphicsContext(getConsole(), this);


        GemMined();
    }

    @Override
    public void onLoop() {
        //Loops every 100ms by default, to change:
        //this.loopDelay = 500;
        LocalPlayer player = Client.getLocalPlayer();
        if (player == null || Client.getGameState() != Client.GameState.LOGGED_IN || botState == BotState.IDLE) {
            //wait some time so we dont immediately start on login.
            Execution.delay(random.nextLong(3000,7000));
            return;
        }

        switch (botState) {
            case IDLE -> {
                //do nothing
                println("We're idle!");
                Execution.delay(random.nextLong(1000,3000));
            }
            case SKILLING -> {

                //do some code that handles your skilling
                //scriptStartTime = Instant.now();
                if(someBool1 !=true) {
                    Execution.delay(handleskillingwithoutseed(player));
                }
                else
                {
                    Execution.delay(handleSkilling(player));
                }
            }
            case BANKING -> {
                //handle your banking logic, etc\
                if (someBool == true)
                {
                    Execution.delay(teleportbanking(player));
                }
                else
                {
                    Execution.delay(handleBanking(player));
                }
            }
        }
    }

    private boolean WalkToAlKharid(LocalPlayer player )
    {
        if(player.getCoordinate().getRegionId() != 13105)
        {
            println("Player not at AlKharid Area");
            println("Region ID" + player.getCoordinate().getRegionId());
            println("Region X" + player.getCoordinate().getX());
            println("Region Y" + player.getCoordinate().getY());
           // Travel.walkTo(3300,3274);


        }
        Coordinate AlKharid1Random= AlKharid1.getRandomCoordinate();
        Travel.walkTo(AlKharid1Random.getX(), AlKharid1Random.getY());
        Execution.delayUntil(20000,() -> {

            assert player != null;
            return AlKharid1.contains(player.getCoordinate());
        });
        Coordinate AlKharid2Random= AlKharid2.getRandomCoordinate();
        Travel.walkTo(AlKharid2Random.getX(), AlKharid2Random.getY());
        Execution.delayUntil(20000,() -> {

            assert player != null;
            return AlKharid2.contains(player.getCoordinate());
        });
        Coordinate AlKharid3Random= AlKharid3.getRandomCoordinate();
        Travel.walkTo(AlKharid3Random.getX(), AlKharid3Random.getY());
        Execution.delayUntil(20000,() -> {

            assert player != null;
            return AlKharid3.contains(player.getCoordinate());
        });
        Coordinate AlKharid4Random= AlKharid4.getRandomCoordinate();
        Travel.walkTo(AlKharid4Random.getX(), AlKharid4Random.getY());
        Execution.delayUntil(10000,() -> {

            assert player != null;
            return AlKharid4.contains(player.getCoordinate());
        });

        if(player.getCoordinate().getRegionId() == 13105 )
        {
            println("AlKharid Area reached");
            println("Cord X" + player.getCoordinate().getX());
            println("Cord Y" + player.getCoordinate().getY());
            return true;
        }
        else
        {
            println("You are not in the area");
            return false;
        }

    }

    private boolean WalkToAlKharidMine(LocalPlayer player)
    {
        Coordinate AlKharid3Random= AlKharid3.getRandomCoordinate();
        Travel.walkTo(AlKharid3Random.getX(), AlKharid3Random.getY());
        Execution.delayUntil(20000,() -> {

            assert player != null;
            return AlKharid3.contains(player.getCoordinate());
        });
        Coordinate AlKharid2Random= AlKharid2.getRandomCoordinate();
        Travel.walkTo(AlKharid2Random.getX(), AlKharid2Random.getY());
        Execution.delayUntil(20000,() -> {

            assert player != null;
            return AlKharid2.contains(player.getCoordinate());
        });
        Coordinate AlKharid1Random= AlKharid1.getRandomCoordinate();
        Travel.walkTo(AlKharid1Random.getX(), AlKharid1Random.getY());
        Execution.delayUntil(20000,() -> {

            assert player != null;
            return AlKharid1.contains(player.getCoordinate());
        });
        if(player.getCoordinate().getRegionId() == 13107 )
        {
            println("AlKharid Mine reached");
            println("Cord X" + player.getCoordinate().getX());
            println("Cord Y" + player.getCoordinate().getY());
            return true;
        }
        else
        {
            println("You are not in the area");
            return false;
        }
    }

    public void GemMined()
    {
        subscribe(InventoryUpdateEvent.class, inventoryUpdateEvent -> {
            Item item = inventoryUpdateEvent.getNewItem();
            //println("New Item in Inventory: " + item);
            if (item != null) {
                if (item.getInventoryType().getId() != 93) {
                    return;
                }
                String runeName = item.getName();
                //println("Rune Name:" + runeName);
                if (runeName != null) {
                    if (runeName.equalsIgnoreCase("Uncut ruby")) {
                        GemMined = GemMined + item.getStackSize();
                        //println("Number of Gem Mined: " + GemMined);
                        //println("Number of items crated" + numberofrunecrated);
                        //equalsIgnoreCase(options[currentItem]
                    }
                    if (runeName.equalsIgnoreCase("Uncut sapphire")) {
                        GemMined = GemMined + item.getStackSize();
                        //println("Number of Gem Mined: " + GemMined);
                        //println("Number of items crated" + numberofrunecrated);
                        //equalsIgnoreCase(options[currentItem]
                    }
                    if (runeName.equalsIgnoreCase("Uncut emerald")) {
                        GemMined = GemMined + item.getStackSize();
                        //println("Number of Gem Mined: " + GemMined);
                        //println("Number of items crated" + numberofrunecrated);
                        //equalsIgnoreCase(options[currentItem]
                    }
                }

            }
            long currenttime = (System.currentTimeMillis() - scriptStartTime) /1000;
            GemMinedPerHour = (int)(Math.round(3600.0 / currenttime * GemMined));
            //println(" Gem's Mined Per Hour: " + GemMinedPerHour);

        });

    }
    private long handleBanking(LocalPlayer player)
    {
        println("War Unlock state" + someBool);
        println("Player moving 1:" +player.isMoving());

        if(player.isMoving())
        {
            return random.nextLong(3000,5000);
        }
        if (Bank.isOpen())
        {
            println("Bank is open");
            Bank.depositAllExcept(54004);
            botState = BotState.SKILLING;
            return random.nextLong(1000,3000);
        }
        if (player.getCoordinate().getRegionId() != 13105)
        {
            WalkToAlKharid(player);
        }
        else
        {
            ResultSet<SceneObject> banks = SceneObjectQuery.newQuery().name("Bank booth").option("Bank").inside(AlKharid).results();
            if (banks.isEmpty())
            {
                println("Bank query was empty.");
            }
            else
            {
                SceneObject bank = banks.random();
                if (bank != null) {
                    println("Yay, we found our bank.");
                    println("Interacted bank: " + bank.interact("Bank"));
                    Bank.depositAllExcept(54004);
                }
            }
        }

        return random.nextLong(1500,3000);
    }
    private long teleportbanking(LocalPlayer player)
    {
        println("War Unlock state" + someBool);
        println("Teleport to War Retreat Area");
        println("Player moving 1:" +player.isMoving());
       // ActionBar.useAbility("War's Retreat Teleport");
        if(player.isMoving())
        {
            return random.nextLong(3000,5000);
        }
        if (Bank.isOpen())
        {
            println("Bank is open");
            Bank.depositAllExcept(54004);
            botState = BotState.SKILLING;
            return random.nextLong(1000,3000);
        }
        if (player.getCoordinate().getRegionId() != 13214)
        {
            ActionBar.useAbility("War's Retreat Teleport");
            Execution.delayUntil( 10000, () -> player.getCoordinate().getRegionId() ==13214);
            if (player.getCoordinate().getRegionId() == 13214)
            {
                println("War's Teleport Successful");
            }
            return random.nextLong(1000,2000);
        }
        else
        {
            SceneObject bankChest = SceneObjectQuery.newQuery().name("Bank chest").results().nearest();
            if (bankChest != null)
            {

                println("Interact with War Bank: " + bankChest.interact("Use"));
                return random.nextLong(1000,3000);
                //Bank.depositAllExcept(54004);

            }
        }
        return random.nextLong(2000,3000);
    }

    private long GemMining()
    {
        if (Skills.MINING.getLevel() <20)
        {
            SceneObject CommonGem = SceneObjectQuery.newQuery().name("Common gem rock").option("Mine").results().random();
            if (CommonGem != null) {

                println("Interacted CommonGem: " + CommonGem.interact("Mine"));

            }
        }
        if (Skills.MINING.getLevel() >=20)
        {
            SceneObject UncommonGem = SceneObjectQuery.newQuery().name("Uncommon gem rock").option("Mine").results().random();
            if (UncommonGem != null) {

                println("Interacted UnCommonGem: " + UncommonGem.interact("Mine"));
            }
        }
        return random.nextLong(1500,3000);
    }

    private long handleSkilling(LocalPlayer player) {
         if (Interfaces.isOpen(1251))
             return random.nextLong(150,3000);
         if (Backpack.isFull())
         {
             println("Going to banking state");
             botState = botState.BANKING;
             return random.nextLong(1500,3000);
         }
        println("Player moving:" +player.isMoving());
        println("Animation ID: " +  player.getAnimationId());
        println("Region ID" + player.getCoordinate().getRegionId());
        //println("Head Bar Value" + player.getHeadbars());
        println("Stam ID value" +player.getHeadbars().get(0).getId());
        println("Stam Width value" +player.getHeadbars().get(0).getWidth());
        if (player.getHeadbars().get(0).getId() == 5 && player.getHeadbars().get(0).getWidth() < random.nextInt(100) )
        {

            println("StamFix");
            GemMining();

        }

         if (player.getAnimationId() != -1 || player.isMoving() && player.getCoordinate().getRegionId() == 13107){
             //println("Region ID" + player.getCoordinate().getRegionId());
             println("Region ID Test" + player.getCoordinate().getRegionId());
             return random.nextLong(3000,5000);
         }
         if (player.getAnimationId() == -1 && player.getCoordinate().getRegionId() != 13107)
         {

             println("Region ID" + player.getCoordinate().getRegionId());
             println("Not in correct Region");
             boolean hasSandSeed = Backpack.contains("Mystical sand seed");
             if(hasSandSeed)
             {
                 println("Found Sand Seed");
                 ActionBar.useItem("Mystical sand seed", "Plant");
                 println("Planted seed");
                 return random.nextLong(1500,3000);
             }

         }
        GemMining();


        return random.nextLong(1500,3000);
    }

    private long handleskillingwithoutseed(LocalPlayer player)
    {
        if (Interfaces.isOpen(1251))
            return random.nextLong(150,3000);
        if (Backpack.isFull())
        {
            println("Going to banking state");
            botState = botState.BANKING;
            return random.nextLong(1500,3000);
        }
        if (player.getHeadbars().get(0).getId() == 5 && player.getHeadbars().get(0).getWidth() < random.nextInt(100) )
        {

            println("StamFix");
            GemMining();

        }
        if (player.getAnimationId() != -1 || player.isMoving() && player.getCoordinate().getRegionId() == 13107){
            //println("Region ID" + player.getCoordinate().getRegionId());
            println("Region ID Test" + player.getCoordinate().getRegionId());
            return random.nextLong(3000,5000);
        }
        if (player.getAnimationId() == -1 && player.getCoordinate().getRegionId() != 13107 && player.getCoordinate().getRegionId() ==13214)
        {

            println("Region ID" + player.getCoordinate().getRegionId());
            println("Not in correct Region");
            if(Lodestone.AL_KHARID.isAvailable())
            {
                Lodestone.AL_KHARID.teleport();
                return random.nextLong(3000, 5000);
            }
            else
            {
                botState= BotState.IDLE;
            }
            WalkToAlKharidMine(player);


        }
        else if (player.getAnimationId() == -1 &&  player.getCoordinate().getRegionId() != 13107 && player.getCoordinate().getRegionId() == 13105)
        {
            WalkToAlKharidMine(player);
            return random.nextLong(3000,5000);
        }
        GemMining();
        return random.nextLong(2000,3000);
    }

    public BotState getBotState() {
        return botState;
    }

    public void setBotState(BotState botState) {
        this.botState = botState;
    }

    public boolean isSomeBool() {
        return someBool;
    }

    public void setSomeBool(boolean someBool) {
        this.someBool = someBool;
    }

    public boolean isSomeBool1() {
        return someBool1;
    }

    public void setSomeBool1(boolean someBool1) {
        this.someBool1 = someBool1;
    }

}