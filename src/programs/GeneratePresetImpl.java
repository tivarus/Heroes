package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {

    @Override
    public Army generate(List<Unit> unitList, int maxPoints) {
        sortUnits(unitList);

        List<Unit> addedUnits = new ArrayList<>();
        int currentPoints = 0;
        for (Unit unit : unitList) {
            int unitsToAdd = countingMaxUnits(unit, maxPoints, currentPoints);
            addUnitsToArmy(unit, unitsToAdd, addedUnits);
            currentPoints += unitsToAdd * unit.getCost();
        }

        setCoordinatesToUnits(addedUnits);
        Army army = new Army();
        army.setUnits(addedUnits);
        army.setPoints(currentPoints);
        return army;
    }

    private void sortUnits(List<Unit> units) {
        units.sort(Comparator.comparingDouble(unit -> -((double) (unit.getBaseAttack() + unit.getHealth()) / unit.getCost())));
    }

    private int countingMaxUnits(Unit unit, int maxPoints, int currentPoints) {
        return Math.min(11, (maxPoints - currentPoints) / unit.getCost());
    }

    private void addUnitsToArmy(Unit unit, int unitsToAdd, List<Unit> selectedUnits) {
        for (int i = 0; i < unitsToAdd; i++) {
            Unit newUnit = createNewUnit(unit, i);
            selectedUnits.add(newUnit);
        }
    }

    // Новый юнит с уникальным именем
    private Unit createNewUnit(Unit unit, int index) {
        Unit newUnit = new Unit(unit.getName(), unit.getUnitType(), unit.getHealth(),
                unit.getBaseAttack(), unit.getCost(), unit.getAttackType(),
                unit.getAttackBonuses(), unit.getDefenceBonuses(), -1, -1);
        newUnit.setName(unit.getUnitType() + " " + index);
        return newUnit;
    }

    private void setCoordinatesToUnits(List<Unit> units) {
        Set<String> coordinates = new HashSet<>();
        Random random = new Random();

        for (Unit unit : units) {
            setRandomCoordinates(unit, coordinates, random);
        }
    }

    // Присвоение юниту случайных координат и проверка на занятость
    private void setRandomCoordinates(Unit unit, Set<String> occupiedCoords, Random random) {
        int coordX, coordY;
        do {
            coordX = random.nextInt(3);
            coordY = random.nextInt(21);
        } while (occupiedCoords.contains(coordX + "," + coordY));
        occupiedCoords.add(coordX + "," + coordY);
        unit.setxCoordinate(coordX);
        unit.setyCoordinate(coordY);
    }
}