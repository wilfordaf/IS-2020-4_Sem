from functools import lru_cache

import numdifftools as nd
from math import sqrt, fabs

INF = 10 ** 9


class Point:
    def __init__(self, x: float, y: float):
        self.x = x
        self.y = y

    def __str__(self):
        return f"x: {self.x}, y: {self.y}"

    def __repr__(self):
        return f"x: {self.x}, y: {self.y}"

    def distance_to_other(self, other):
        return sqrt((self.x - other.x) ** 2 + (self.y - other.y) ** 2)

    def func_distance_to_other(self, other):
        return fabs(Solution.__func__([self.x, self.y]) - Solution.__func__([other.x, other.y]))

    def to_list(self):
        return [self.x, self.y]

    def copy(self):
        return Point(self.x, self.y)

    def __add__(self, other):
        if type(other) != Point:
            raise ValueError

        return Point(self.x + other.x, self.y + other.y)

    def __sub__(self, other):
        if type(other) != Point:
            raise ValueError

        return Point(self.x - other.x, self.y - other.y)

    def __mul__(self, number):
        return Point(self.x * number, self.y * number)

    def __truediv__(self, number):
        return Point(self.x / number, self.y / number)

    def __pow__(self, power, modulo=None):
        return self.x ** power + self.y ** power


class Solution:
    def __init__(self, start_point: Point, precision: float):
        self.__start_point = start_point
        self.__precision = precision

    def constant_learning_rate_method(self, learning_rate):
        prev_point = Point(INF, INF)
        optimization_point = self.__start_point.copy()
        amount_func_calculations = 0
        optimization_points = []

        while True:
            optimization_points.append(optimization_point.copy())

            amount_func_calculations += 2
            if (optimization_point.func_distance_to_other(prev_point) <= self.__precision or
                    optimization_point.distance_to_other(prev_point) <= self.__precision):
                return optimization_points, amount_func_calculations

            prev_point = optimization_point.copy()
            x, y = nd.Gradient(self.__func__)(optimization_point.to_list())
            optimization_point.x -= x * learning_rate
            optimization_point.y -= y * learning_rate
            amount_func_calculations += 1

    def step_fragmentation_method(self, const, fragmentation_const, start_learning_rate):
        prev_point = Point(INF, INF)
        optimization_point = self.__start_point.copy()
        amount_func_calculations = 0
        learning_rate = start_learning_rate
        optimization_points = []

        while True:
            optimization_points.append(optimization_point.copy())

            amount_func_calculations += 2
            if (optimization_point.func_distance_to_other(prev_point) <= self.__precision or
                    optimization_point.distance_to_other(prev_point) <= self.__precision):
                return optimization_points, amount_func_calculations

            prev_point = optimization_point.copy()
            x, y = nd.Gradient(self.__func__)(optimization_point.to_list())
            optimization_point.x -= x * learning_rate
            optimization_point.y -= y * learning_rate
            amount_func_calculations += 1

            func_of_point = self.__func__([optimization_point.x, optimization_point.y])
            func_of_prev_point = (self.__func__([prev_point.x, prev_point.y]) - const * learning_rate * fabs(
                self.__der_func__([prev_point.x, prev_point.y])))
            amount_func_calculations += 3

            while func_of_point > func_of_prev_point:
                learning_rate *= fragmentation_const
                func_of_prev_point = (self.__func__([prev_point.x, prev_point.y]) - const * learning_rate * fabs(
                    self.__der_func__([prev_point.x, prev_point.y])))
                amount_func_calculations += 2

    def golden_ratio_method(self):
        prev_point = Point(INF, INF)
        optimization_point = self.__start_point.copy()
        amount_func_calculations = 0
        optimization_points = []

        while True:
            optimization_points.append(optimization_point.copy())

            amount_func_calculations += 2
            if (optimization_point.func_distance_to_other(prev_point) <= self.__precision or
                    optimization_point.distance_to_other(prev_point) <= self.__precision):
                return optimization_points, amount_func_calculations

            prev_point = optimization_point.copy()

            x, y = nd.Gradient(self.__func__)(optimization_point.to_list())
            gradient = Point(x, y)

            learning_rate, search_amount_calc = self.__golden_search__(optimization_point, gradient, 0, 1)
            amount_func_calculations += search_amount_calc

            optimization_point.x -= x * learning_rate
            optimization_point.y -= y * learning_rate

    def fibonacci_method(self):
        prev_point = Point(INF, INF)
        optimization_point = self.__start_point.copy()
        amount_func_calculations = 0
        optimization_points = []

        while True:
            optimization_points.append(optimization_point.copy())

            amount_func_calculations += 2
            if (optimization_point.distance_to_other(prev_point) <= self.__precision or
                    optimization_point.func_distance_to_other(prev_point) <= self.__precision):
                return optimization_points, amount_func_calculations

            prev_point = optimization_point.copy()

            x, y = nd.Gradient(self.__func__)(optimization_point.to_list())
            gradient = Point(x, y)

            learning_rate, search_amount_calc = self.__fibonacci_search__(optimization_point, gradient, 0, 1)
            amount_func_calculations += search_amount_calc

            optimization_point.x -= x * learning_rate
            optimization_point.y -= y * learning_rate

    def fletcher_reeves_method(self):
        amount_func_calculations = 0

        optimization_point = self.__start_point.copy()
        x, y = nd.Gradient(self.__func__)(optimization_point.to_list())
        gradient = Point(x, y)
        gradient_square = gradient ** 2

        optimization_points = []

        amount_func_calculations += 2
        new_x, new_y = x, y

        new_x, new_y = x, y

        while True:
            optimization_points.append(optimization_point.copy())

            if gradient_square <= self.__precision:
                return optimization_points, amount_func_calculations

            learning_rate, search_amount_calc = self.__fibonacci_search__(optimization_point, gradient, 0, 1)

            amount_func_calculations += search_amount_calc

            optimization_point.x -= new_x * learning_rate
            optimization_point.y -= new_y * learning_rate

            new_x, new_y = nd.Gradient(self.__func__)(optimization_point.to_list())
            new_gradient = Point(new_x, new_y)
            new_gradient_square = new_gradient ** 2
            amount_func_calculations += 1

            beta = new_gradient_square / gradient_square

            gradient = new_gradient + gradient * beta
            gradient_square = gradient ** 2
            print(gradient_square)

    def __golden_search__(self, point, gradient, left_border, right_border):
        amount_func_calculations = 0

        ratio_const = (3 - sqrt(5)) / 2
        left_ratio_point = left_border + (right_border - left_border) * ratio_const
        right_ratio_point = right_border - (right_border - left_border) * ratio_const

        while fabs(left_border - right_border) > self.__precision:
            amount_func_calculations += 2
            func_of_left = self.__func__((point - gradient * left_ratio_point).to_list())
            func_of_right = self.__func__((point - gradient * right_ratio_point).to_list())

            if func_of_left < func_of_right:
                right_border = right_ratio_point
            else:
                left_border = left_ratio_point

            left_ratio_point = left_border + (right_border - left_border) * ratio_const
            right_ratio_point = right_border - (right_border - left_border) * ratio_const

        return (left_border + right_border) / 2, amount_func_calculations

    def __fibonacci_search__(self, point, gradient, left_border, right_border):
        interval_length = right_border - left_border
        iteration_amount = 0

        while self.__fibonacci__(iteration_amount) <= interval_length / self.__precision:
            iteration_amount += 1

        left_fib_point = left_border + self.__fibonacciRatio__(
            iteration_amount,
            iteration_amount + 2) * interval_length

        right_fib_point = left_border + self.__fibonacciRatio__(
            iteration_amount + 1,
            iteration_amount + 2) * interval_length

        func_of_left_point = self.__func__((point - gradient * left_fib_point).to_list())
        func_of_right_point = self.__func__((point - gradient * right_fib_point).to_list())

        counter = 0
        while counter != iteration_amount:
            if func_of_left_point > func_of_right_point:
                left_border = left_fib_point
                interval_length = right_border - left_border
                left_fib_point, func_of_left_point = right_fib_point, func_of_right_point
                right_fib_point = left_border + self.__fibonacciRatio__(
                    iteration_amount - counter + 1,
                    iteration_amount - counter + 2) * interval_length
                func_of_right_point = self.__func__((point - gradient * right_fib_point).to_list())
            else:
                right_border = right_fib_point
                interval_length = right_border - left_border
                right_fib_point, func_of_right_point = left_fib_point, func_of_left_point
                left_fib_point = left_border + self.__fibonacciRatio__(
                    iteration_amount - counter,
                    iteration_amount - counter + 2) * interval_length
                func_of_left_point = self.__func__((point - gradient * left_fib_point).to_list())

            counter += 1

        if func_of_left_point == func_of_right_point:
            left_border = left_fib_point
        else:
            right_border = right_fib_point

        return (right_border + left_border) / 2, iteration_amount + 2

    @staticmethod
    def __func__(args):
        return args[0] ** 2 + 5 * args[1] ** 2

    @staticmethod
    def __der_func__(args):
        return 2 * args[0] + 10 * args[1]

    @lru_cache(maxsize=1000)
    def __fibonacci__(self, n):
        return 1 if n in [0, 1] else self.__fibonacci__(n - 1) + self.__fibonacci__(n - 2)

    def __fibonacciRatio__(self, n1, n2):
        return self.__fibonacci__(n1) / self.__fibonacci__(n2)
