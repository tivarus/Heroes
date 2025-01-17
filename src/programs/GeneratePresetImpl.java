package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {

    @Override
    public Army generate(List<Unit> units, int maxPoints) {
        Army army = new Army();
        List<Unit> selectedUnits = new ArrayList<>();
        int currentPoints = 0;

        units.sort(Comparator.comparingDouble(this::calculateEffectiveness).reversed());

        for (Unit unit : units) {
            int unitsToAdd = Math.min(11, (maxPoints - currentPoints) / unit.getCost());
            addUnits(unit, unitsToAdd, selectedUnits);
            currentPoints += unitsToAdd * unit.getCost();
        }

        assignCoordinates(selectedUnits);
        army.setUnits(selectedUnits);
        army.setPoints(currentPoints);
        return army;
    }
    private double calculateEffectiveness(Unit unit) {
        return (double) (unit.getBaseAttack() + unit.getHealth()) / unit.getCost();
    }

    private void addUnits(Unit unit, int count, List<Unit> selectedUnits) {
        for (int i = 0; i < count; i++) {
            selectedUnits.add(createUnit(unit, i));
        }
    }

    private Unit createUnit(Unit unit, int index) {
        Unit newUnit = new Unit(unit.getName(), unit.getUnitType(), unit.getHealth(),
                unit.getBaseAttack(), unit.getCost(), unit.getAttackType(),
                unit.getAttackBonuses(), unit.getDefenceBonuses(), -1, -1);
        newUnit.setName(unit.getUnitType() + " " + index);
        return newUnit;
    }

    private void assignCoordinates(List<Unit> units) {
        Set<String> occupiedCoordinates = new HashSet<>();
        Random random = new Random();

        for (Unit unit : units) {
            int coordX, coordY;
            do {
                coordX = random.nextInt(3);
                coordY = random.nextInt(21);
            } while (!occupiedCoordinates.add(coordX + "," + coordY));
            unit.setxCoordinate(coordX);
            unit.setyCoordinate(coordY);
        }
    }
}