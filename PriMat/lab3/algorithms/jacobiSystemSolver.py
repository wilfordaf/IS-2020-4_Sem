from lab3.entities.scrMatrix import SCRMatrix


class JacobiSystemSolver:
    def __init__(self, a: SCRMatrix, b: [], precision: float):
        self.a = a
        self.b = b
        self.precision = precision

    def solve(self) -> []:
        n = self.a.get_n()
        x = [0 for _ in range(n)]
        for _ in range(n * (n + 1)):
        # while True:
            new_x = [0 for _ in range(n)]
            for i in range(n):
                si = 0
                for j in range(n):
                    if i != j:
                        si += x[j] * self.a.get(i, j)

                new_x[i] = (self.b[i] - si) / self.a.get(i, i)

            if self.__range_between_arrays(x, new_x):
                return new_x

            x = new_x
        return x

    def __range_between_arrays(self, arr1: [], arr2: []):
        if len(arr1) != len(arr2):
            raise IndexError("Array dimensions are not equal")

        elements_sum = 0
        for val1, val2 in zip(arr1, arr2):
            elements_sum += (val1 - val2) ** 2

        return elements_sum < self.precision
