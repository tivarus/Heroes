package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;

import java.util.*;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {

    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        Map<Integer, List<Unit>> suitableUnitsMap = new HashMap<>();

        for (int i = 0; i < unitsByRow.size(); i++) {
            List<Unit> row = unitsByRow.get(i);
            List<Unit> suitableUnitsInRow = findSuitableUnitsInRow(row, isLeftArmyTarget);
            if (!suitableUnitsInRow.isEmpty()) {
                suitableUnitsMap.put(i, suitableUnitsInRow);
            }
        }

        List<Unit> allSuitableUnits = new ArrayList<>();
        suitableUnitsMap.values().forEach(allSuitableUnits::addAll);
        return allSuitableUnits;
    }
    // Поиск подходящих юнитов в строке
    private List<Unit> findSuitableUnitsInRow(List<Unit> row, boolean isLeftArmyTarget) {
        List<Unit> suitableUnits = new ArrayList<>();
        for (int index = 0; index < row.size(); index++) {
            Unit unit = row.get(index);
            if (unit != null && unit.isAlive() &&
                    (isLeftArmyTarget ? isRightmostUnit(row, index) : isLeftmostUnit(row, index))) {
                suitableUnits.add(unit);
            }
        }
        return suitableUnits;
    }

    // Проверка, является ли юнит самым правым в ряду
    private boolean isRightmostUnit(List<Unit> row, int unitIndex) {
        return unitIndex == row.size() - 1 || row.subList(unitIndex + 1, row.size()).stream().allMatch(Objects::isNull);
    }

    // Проверка, является ли юнит самым левым в ряду
    private boolean isLeftmostUnit(List<Unit> row, int unitIndex) {
        return unitIndex == 0 || row.subList(0, unitIndex).stream().allMatch(Objects::isNull);
    }
}
