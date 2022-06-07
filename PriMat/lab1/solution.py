from functools import lru_cache
from math import sin, log, sqrt, fabs

import matplotlib.pyplot as plt


def drawGraph(func):
    def wrapper(solution):
        result = func(solution)

        print(f"Minimum found by {func.__name__}, value is: {result[0]}")
        plt.plot([i for i in range(len(result[1]))], result[1], label=func.__name__)

        return result

    return wrapper


class Solution:
    def __init__(self, leftBorder: float, rightBorder: float, precision: float):
        self.__leftBorder = leftBorder
        self.__rightBorder = rightBorder
        self.__precision = precision
        self.__GOLDEN_RATIO = (3 - sqrt(5)) / 2

    @drawGraph
    def bisectionMethod(self):
        indent = self.__precision / 2.01
        leftBorder = self.__leftBorder
        rightBorder = self.__rightBorder

        intervalLengths = []

        while not intervalLengths or intervalLengths[-1] >= self.__precision:
            intervalLengths.append(rightBorder - leftBorder)
            middle = (leftBorder + rightBorder) / 2

            indentToLeft = middle - indent
            indentToRight = middle + indent

            if self.__func__(indentToLeft) >= self.__func__(indentToRight):
                leftBorder = indentToLeft
            else:
                rightBorder = indentToRight

        return (leftBorder + rightBorder) / 2, intervalLengths

    @drawGraph
    def goldenRatioMethod(self):
        leftBorder = self.__leftBorder
        rightBorder = self.__rightBorder

        intervalLengths = []

        leftRatioPoint = leftBorder + (rightBorder - leftBorder) * self.__GOLDEN_RATIO
        rightRatioPoint = rightBorder - (rightBorder - leftBorder) * self.__GOLDEN_RATIO

        funcOfLeftPoint = self.__func__(leftRatioPoint)
        funcOfRightPoint = self.__func__(rightRatioPoint)

        while not intervalLengths or intervalLengths[-1] >= self.__precision:
            intervalLengths.append(rightBorder - leftBorder)
            if funcOfLeftPoint < funcOfRightPoint:
                rightBorder = rightRatioPoint
                rightRatioPoint, funcOfRightPoint = leftRatioPoint, funcOfLeftPoint
                leftRatioPoint = leftBorder + (rightBorder - leftBorder) * self.__GOLDEN_RATIO
                funcOfLeftPoint = self.__func__(leftRatioPoint)
            else:
                leftBorder = leftRatioPoint
                leftRatioPoint, funcOfLeftPoint = rightRatioPoint, funcOfRightPoint
                rightRatioPoint = rightBorder - (rightBorder - leftBorder) * self.__GOLDEN_RATIO
                funcOfRightPoint = self.__func__(rightRatioPoint)

        return (leftBorder + rightBorder) / 2, intervalLengths

    @drawGraph
    def fibonacciMethod(self):
        leftBorder = self.__leftBorder
        rightBorder = self.__rightBorder
        intervalLength = rightBorder - leftBorder

        intervalLengths = [intervalLength]

        iterationAmount = 0
        while self.__fibonacci__(iterationAmount) <= intervalLength / self.__precision:
            iterationAmount += 1

        leftFibPoint = leftBorder + self.__fibonacciRatio__(iterationAmount, iterationAmount + 2) * intervalLength
        rightFibPoint = leftBorder + self.__fibonacciRatio__(iterationAmount + 1, iterationAmount + 2) * intervalLength

        funcOfLeftPoint = self.__func__(leftFibPoint)
        funcOfRightPoint = self.__func__(rightFibPoint)

        counter = 0
        while counter != iterationAmount:
            if funcOfLeftPoint > funcOfRightPoint:
                leftBorder = leftFibPoint
                intervalLength = rightBorder - leftBorder
                leftFibPoint, funcOfLeftPoint = rightFibPoint, funcOfRightPoint
                rightFibPoint = leftBorder + self.__fibonacciRatio__(
                    iterationAmount - counter + 1,
                    iterationAmount - counter + 2) * intervalLength
                funcOfRightPoint = self.__func__(rightFibPoint)
            else:
                rightBorder = rightFibPoint
                intervalLength = rightBorder - leftBorder
                rightFibPoint, funcOfRightPoint = leftFibPoint, funcOfLeftPoint
                leftFibPoint = leftBorder + self.__fibonacciRatio__(
                    iterationAmount - counter,
                    iterationAmount - counter + 2) * intervalLength
                funcOfLeftPoint = self.__func__(leftFibPoint)

            counter += 1
            intervalLengths.append(intervalLength)

        if self.__func__(leftFibPoint) == self.__func__(rightFibPoint):
            leftBorder = leftFibPoint
        else:
            rightBorder = rightFibPoint

        intervalLengths.append(rightBorder - leftBorder)

        return (rightBorder + leftBorder) / 2, intervalLengths

    @drawGraph
    def parablesMethod(self):
        leftBorder = self.__leftBorder
        rightBorder = self.__rightBorder
        parabolaMin = 0

        intervalLengths = []

        funcOfLeftBorder = self.__func__(leftBorder)
        funcOfRightBorder = self.__func__(rightBorder)

        middle = (leftBorder + rightBorder) / 2
        funcOfMid = self.__func__(middle)

        while not intervalLengths or intervalLengths[-1] >= self.__precision:
            prevMin = parabolaMin
            intervalLengths.append(rightBorder - leftBorder)

            numerator = ((middle - leftBorder) ** 2 * (funcOfMid - funcOfRightBorder)
                         - (middle - rightBorder) ** 2 * (funcOfMid - funcOfLeftBorder))

            denominator = 2 * ((middle - leftBorder) * (funcOfMid - funcOfRightBorder)
                               - (middle - rightBorder) * (funcOfMid - funcOfLeftBorder))

            parabolaMin = middle - (numerator / denominator)

            if prevMin:
                delta = parabolaMin - prevMin
                if fabs(delta) < self.__precision:
                    return parabolaMin, intervalLengths

            funcOfParabolaMin = self.__func__(parabolaMin)
            if fabs(parabolaMin - leftBorder) >= fabs(parabolaMin - rightBorder):
                rightBorder, funcOfRightBorder = parabolaMin, funcOfParabolaMin
            else:
                leftBorder, funcOfLeftBorder = middle, funcOfMid
                middle, funcOfMid = parabolaMin, funcOfParabolaMin

        return (rightBorder + leftBorder) / 2, intervalLengths

    @drawGraph
    def brentMethod(self):
        leftBorder = self.__leftBorder
        rightBorder = self.__rightBorder

        minimum = secondMin = thirdMin = (leftBorder + rightBorder) / 2
        funcOfMin = funcOfSecondMin = funcOfThirdMin = self.__func__(minimum)

        intervalLengths = []

        while not intervalLengths or intervalLengths[-1] >= self.__precision:
            intervalLengths.append(rightBorder - leftBorder)

            if funcOfMin not in [funcOfSecondMin, funcOfThirdMin] and funcOfSecondMin != funcOfThirdMin:
                numerator = ((secondMin - minimum) ** 2 * (funcOfSecondMin - funcOfThirdMin)
                             - (secondMin - thirdMin) ** 2 * (funcOfSecondMin - funcOfMin))

                denominator = 2 * ((secondMin - minimum) * (funcOfSecondMin - funcOfThirdMin)
                                   - (secondMin - thirdMin) * (funcOfSecondMin - funcOfMin))

                parabolaMin = secondMin - (numerator / denominator)

            else:
                if minimum < (rightBorder - leftBorder) / 2:
                    parabolaMin = minimum + self.__GOLDEN_RATIO * (rightBorder - minimum)
                else:
                    parabolaMin = minimum - self.__GOLDEN_RATIO * (minimum - leftBorder)

            if fabs(parabolaMin - minimum) < self.__precision:
                return parabolaMin, intervalLengths

            funcOfParabolaMin = self.__func__(parabolaMin)
            if funcOfParabolaMin <= funcOfMin:
                if parabolaMin >= minimum:
                    leftBorder = minimum
                else:
                    rightBorder = minimum

                thirdMin, funcOfThirdMin = secondMin, funcOfSecondMin
                secondMin, funcOfSecondMin = minimum, funcOfMin
                minimum, funcOfMin = parabolaMin, funcOfParabolaMin
            else:
                if parabolaMin >= minimum:
                    rightBorder = minimum
                else:
                    leftBorder = minimum

                if funcOfParabolaMin <= funcOfSecondMin or secondMin == minimum:
                    thirdMin, funcOfThirdMin = secondMin, funcOfSecondMin
                    secondMin, funcOfSecondMin = parabolaMin, funcOfParabolaMin
                elif funcOfParabolaMin <= funcOfSecondMin or thirdMin in [minimum, secondMin]:
                    thirdMin, funcOfThirdMin = parabolaMin, funcOfParabolaMin

        return (leftBorder + rightBorder) / 2, intervalLengths

    @lru_cache(maxsize=1000)
    def __fibonacci__(self, n):
        return 1 if n in [0, 1] else self.__fibonacci__(n - 1) + self.__fibonacci__(n - 2)

    def __fibonacciRatio__(self, n1, n2):
        return self.__fibonacci__(n1) / self.__fibonacci__(n2)

    @staticmethod
    def __func__(x):
        return sin(0.5 * log(x) * x) + 1
