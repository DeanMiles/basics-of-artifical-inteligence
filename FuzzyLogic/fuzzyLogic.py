import numpy as np
import skfuzzy as fuzz
from skfuzzy import control as ctrl
from typing import Final
import math

# Alan Franas
def countWithTwoInputedVariables(throwRange, angle):
    score = math.sqrt((int(throwRange) * 10) / pow(math.sin(int(angle)), 2))
    print("Velacity : ")
    print(score)


def fun1():
    throwRangeMembership = ctrl.Antecedent(np.arange(0, 101, 1), 'throwRange')
    angleMembership = ctrl.Antecedent(np.arange(0, 91, 1), 'angle')
    beginVelocity = ctrl.Consequent(np.arange(0, 100, 1), 'beginVelocity')

    throwRangeMembership.automf(5)
    angleMembership.automf(3)

    beginVelocity['very low'] = fuzz.trapmf(beginVelocity.universe, [0, 0, 20, 30])
    beginVelocity['low'] = fuzz.trapmf(beginVelocity.universe, [20, 30, 50, 60])
    beginVelocity['medium'] = fuzz.trapmf(beginVelocity.universe, [50, 60, 80, 90])
    beginVelocity['high'] = fuzz.trapmf(beginVelocity.universe, [80, 90, 100, 100])

    rule1 = ctrl.Rule( (throwRangeMembership['poor'] | throwRangeMembership['mediocre']) & (angleMembership['good'] | angleMembership['poor']),
                      beginVelocity['very low'])
    rule2 = ctrl.Rule( throwRangeMembership['poor']  & angleMembership['average'],
                       beginVelocity['low']);
    rule3 = ctrl.Rule((angleMembership['good'] | angleMembership['poor']) & (throwRangeMembership['average'] | throwRangeMembership['decent']),
                      beginVelocity['low'])
    rule4 = ctrl.Rule( (throwRangeMembership['mediocre'] | throwRangeMembership['average'] | throwRangeMembership['decent'])
                       & angleMembership['average'],
                       beginVelocity['medium']);
    rule5 = ctrl.Rule(angleMembership['average'] & throwRangeMembership['good'] ,
                      beginVelocity['high'])

    rule1.view()

    throwing_ctrl = ctrl.ControlSystem([rule1, rule2, rule3, rule4,  rule5])
    throwing = ctrl.ControlSystemSimulation(throwing_ctrl)
    throwing.input['angle'] = int(input("Input thror range(d)\n"))
    throwing.input['throwRange'] = int(input("Input angle of throw(a):\n"))
    throwing.compute()

    print(throwing.output['beginVelocity'])
    beginVelocity.view(sim=throwing)
    # countWithTwoInputedVariables(80, 90)

def fun2():
    throwRangeMembership = ctrl.Antecedent(np.arange(0, 101, 0.1), 'throwRange')
    airResistanceMembership = ctrl.Antecedent(np.arange(0, 2, 0.01), 'airResistance')
    angleMembership = ctrl.Antecedent(np.arange(0, 91, 1), 'angle')
    bodyWeightMembership = ctrl.Antecedent(np.arange(0, 101, 1), 'bodyWeight')
    beginVelocity = ctrl.Consequent(np.arange(0, 101, 1), 'beginVelocity')

    throwRangeMembership.automf(3)
    airResistanceMembership.automf(3)
    angleMembership.automf(3)
    bodyWeightMembership.automf(3)

    # smaller values ​​at extreme intervals
    beginVelocity['very low'] = fuzz.trapmf(beginVelocity.universe, [0, 0, 10, 15])
    beginVelocity['low'] = fuzz.trapmf(beginVelocity.universe, [10, 15, 35, 40])
    beginVelocity['medium'] = fuzz.trapmf(beginVelocity.universe, [35, 40, 60, 65])
    beginVelocity['high'] = fuzz.trapmf(beginVelocity.universe, [60, 65, 85, 90])
    beginVelocity['very high'] = fuzz.trapmf(beginVelocity.universe, [85, 90, 100, 100])

    # extreme value - very low
    rule1 = ctrl.Rule(bodyWeightMembership['good'] & airResistanceMembership['good']
                      & throwRangeMembership['poor'] & (angleMembership['good'] | angleMembership['poor']),
                      beginVelocity['very low'])
    # low
    rule2 = ctrl.Rule(bodyWeightMembership['good'] & airResistanceMembership['good']
                      & throwRangeMembership['poor'] & (angleMembership['average']),
                      beginVelocity['low'])
    rule3 = ctrl.Rule(bodyWeightMembership['good'] & airResistanceMembership['good']
                      & (throwRangeMembership['average'] | throwRangeMembership['good']) & (angleMembership['good'] | angleMembership['poor']),
                      beginVelocity['low'])
    rule4 = ctrl.Rule( (bodyWeightMembership['average'] | bodyWeightMembership['poor']) & airResistanceMembership['good']
                      & throwRangeMembership['poor'] & (angleMembership['good'] | angleMembership['poor']),
                      beginVelocity['low'])
    rule5 = ctrl.Rule(bodyWeightMembership['good'] & (airResistanceMembership['average'] | airResistanceMembership['poor'])
                      & throwRangeMembership['poor'] & (angleMembership['good'] | angleMembership['poor']),
                      beginVelocity['low'])

    # medium
    rule6 = ctrl.Rule(  (bodyWeightMembership['average'] & throwRangeMembership['average'])
                      | (bodyWeightMembership['average'] & airResistanceMembership['average'])
                      | (throwRangeMembership['average'] & airResistanceMembership['average']),
                      beginVelocity['medium'])

    # medium
    rule7 = ctrl.Rule(  (bodyWeightMembership['poor'] & airResistanceMembership['poor'] & (throwRangeMembership['poor'] | throwRangeMembership['average'])
                         & (angleMembership['good'] | angleMembership['poor']))
                      | ((bodyWeightMembership['good'] | bodyWeightMembership['average']) & airResistanceMembership['poor'] & throwRangeMembership['good']
                         & (angleMembership['good'] | angleMembership['poor']))
                      | (bodyWeightMembership['poor'] & (airResistanceMembership['good'] | airResistanceMembership['average']) & throwRangeMembership['poor']
                         & (angleMembership['good'] | angleMembership['poor']))
                      | (bodyWeightMembership['good'] & airResistanceMembership['good'] & (throwRangeMembership['good'] | throwRangeMembership['average'])
                         & angleMembership['average'])
                      | (bodyWeightMembership['good'] & (airResistanceMembership['poor'] | airResistanceMembership['average']) & throwRangeMembership['poor']
                         & angleMembership['average'])
                      | ((bodyWeightMembership['poor'] | bodyWeightMembership['average']) & airResistanceMembership['good'] & throwRangeMembership['poor'] & angleMembership['average']),
                      beginVelocity['medium'])


    # high
    rule8 = ctrl.Rule((bodyWeightMembership['average'] | bodyWeightMembership['good']) & airResistanceMembership['poor']
                      & throwRangeMembership['good'] & angleMembership['average'],
                      beginVelocity['high'])
    rule9 = ctrl.Rule(bodyWeightMembership['poor'] & (airResistanceMembership['average'] | airResistanceMembership['good'])
                      & throwRangeMembership['good'] & angleMembership['average'],
                      beginVelocity['high'])
    rule10 = ctrl.Rule(bodyWeightMembership['poor'] & airResistanceMembership['poor']
                      & (throwRangeMembership['average'] | throwRangeMembership['poor']) & angleMembership['average'],
                      beginVelocity['high'])
    rule11 = ctrl.Rule(bodyWeightMembership['poor'] & airResistanceMembership['poor']
                      & throwRangeMembership['good'] & (angleMembership['good'] | angleMembership['poor']),
                      beginVelocity['high'])

    # very high
    rule12 = ctrl.Rule(bodyWeightMembership['poor'] & airResistanceMembership['poor']
                      & throwRangeMembership['good'] & angleMembership['average'],
                      beginVelocity['very high'])

    throwing_ctrl = ctrl.ControlSystem([rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8, rule9, rule10, rule11, rule12])
    throwing = ctrl.ControlSystemSimulation(throwing_ctrl)
    print("To make result reliable stictk to the gicen ranges")
    throwing.input['angle'] = int(input("Input angle of throw(a) (0-90):\n"))
    throwing.input['throwRange'] = int(input("Input throw range(d) (0-100):\n"))
    throwing.input['airResistance'] = float(input("Input air resistance(k) (0-1):\n"))
    throwing.input['bodyWeight'] = int(input("Input body weight(m) (0-100):\n"))
    throwing.compute()

    print(throwing.output['beginVelocity'])
    beginVelocity.view(sim=throwing)


def main():
    choice = 1
    while (choice != 0):
        choice = int(input("Which part of task are you intrested in? [1/2/0-exit]:\n"))
        if choice == 1:
            fun1()
        elif choice == 2:
            fun2()
        elif choice == 0:
            exit()
        else:
            print("Wrong input")


if __name__ == "__main__":
    main()
