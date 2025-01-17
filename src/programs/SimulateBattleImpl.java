package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

import java.util.HashSet;
import java.util.Set;

public class SimulateBattleImpl implements SimulateBattle {
    private PrintBattleLog printBattleLog;

    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {
        Set<Unit> playerUnits = new HashSet<>(playerArmy.getUnits());
        Set<Unit> computerUnits = new HashSet<>(computerArmy.getUnits());

        while (!playerUnits.isEmpty() && !computerUnits.isEmpty()) {
            processAttacks(playerUnits);
            processAttacks(computerUnits);
        }
    }

    private void processAttacks(Set<Unit> attackingUnits) throws InterruptedException {
        for (Unit attackingUnit : attackingUnits) {
            if (attackingUnit.isAlive()) {
                Unit target = attackingUnit.getProgram().attack();
                if (target != null) {
                    printBattleLog.printBattleLog(attackingUnit, target);
                }
            }
        }
        attackingUnits.removeIf(unit -> !unit.isAlive());
    }
}