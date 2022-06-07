from lab3.algorithms.luDecomposition import LUDecomposition
from lab3.entities.scrMatrix import SCRMatrix


class GaussSystemSolver:
    def __init__(self, a: SCRMatrix, b: []):
        self.a = a
        self.b = b
        (self.l, self.u) = LUDecomposition(a).decompose()

    def solve(self) -> []:
        y = self.calculate_y()
        x = self.calculate_x(y)
        return x

    def calculate_y(self) -> []:
        n = self.a.get_n()
        y = [self.b[0] / self.l.get(0, 0)]

        for i in range(1, n):
            yi = self.b[i]
            for j in range(i):
                yi -= y[j] * self.l.get(i, j)

            y.append(yi / self.l.get(i, i))

        return y

    def calculate_x(self, y: []) -> []:
        n = self.a.get_n()
        x = [0 for _ in range(n)]
        x[n - 1] = (y[n - 1] / self.u.get(n - 1, n - 1))

        for i in range(n - 1, -1, -1):
            xi = y[i]
            for j in range(n - 1, i, -1):
                xi -= x[j] * self.u.get(i, j)
            x[i] = (xi / self.u.get(i, i))

        return x
