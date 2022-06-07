from lab3.entities import scrMatrix
from lab3.entities.squareMatrix import SquareMatrix


class GilbertMatrix:
    def __init__(self, n: int):
        self.n = n
        self.arr = [[] for _ in range(n)]

    def fill(self):
        for i in range(self.n):
            for j in range(self.n):
                self.arr[i].append(1 / (i + j + 2 - 1))

        return self

    def to_SCRMatrix(self):
        data = []
        ind = []
        ind_ptr = [1]

        for row in self.arr:
            count_not_zero = 0
            for i in range(len(row)):
                if row[i] == 0:
                    continue

                data.append(row[i])
                ind.append(i)
                count_not_zero += 1
            ind_ptr.append(ind_ptr[-1] + count_not_zero)

        return scrMatrix.SCRMatrix(data, ind, ind_ptr)

    def transpose(self):
        for i in range(len(self.arr)):
            for j in range(i):
                self.arr[i][j], self.arr[j][i] = self.arr[j][i], self.arr[i][j]
        return self

    def print(self):
        for row in self.arr:
            print(row)

    def get_n(self):
        return len(self.arr)

    def __getitem__(self, index: int) -> []:
        return self.arr[index]

    def __mul__(self, other: 'SquareMatrix') -> 'SquareMatrix':
        n = len(self.arr)
        matrix = SquareMatrix.of_size(n)
        for i in range(n):
            for j in range(n):
                for k in range(n):
                    matrix.arr[i][j] += self.arr[i][k] * other.arr[k][j]

        return matrix
