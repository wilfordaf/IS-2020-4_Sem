from lab3.entities.scrMatrix import SCRMatrix

from copy import deepcopy


class SeidelSystemSolver:
    def __init__(self, a: SCRMatrix, b: [], precision: float):
        self.a = a
        self.b = b
        self.precision = precision

    def solve(self) -> []:
        n = self.a.get_n()
        x = [0 for _ in range(n)]
        while True:
            new_x = deepcopy(x)
            for i in range(n):
                s1 = sum(self.a.get(i, j) * new_x[j] for j in range(i))
                s2 = sum(self.a.get(i, j) * x[j] for j in range(i + 1, n))
                new_x[i] = (self.b[i] - s1 - s2) / self.a.get(i, i)

            if self.__range_between_arrays(x, new_x):
                return new_x

            x = new_x

        # return x

    def __range_between_arrays(self, arr1: [], arr2: []):
        if len(arr1) != len(arr2):
            raise IndexError("Array dimensions are not equal")

        elements_sum = 0
        for val1, val2 in zip(arr1, arr2):
            elements_sum += (val1 - val2) ** 2

        return elements_sum < self.precision
