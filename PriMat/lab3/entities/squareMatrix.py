import lab3.entities.scrMatrix as scrMatrix
import random


class SquareMatrix:
    def __init__(self, arr: [[]]):
        self.arr = arr

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
        n = self.get_n()
        result = SquareMatrix([[0 for _ in range(n)] for _ in range(n)])

        for i in range(n):
            for j in range(n):
                result[i][j] = self[j][i]

        return result

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

    @staticmethod
    def of_size(n: int):
        return SquareMatrix([[0 for _ in range(n)] for _ in range(n)])

    @staticmethod
    def from_SCRMatrix(matrix):
        return matrix.to_SquareMatrix()

    @staticmethod
    def fill_diagonal_advantage(n: int):
        matrix = SquareMatrix([[] for _ in range(n)])
        for i in range(n):
            for j in range(n):
                if i == j:
                    matrix[i].append(random.randint(n // 2, n * 2) * 10**2)
                else:
                    matrix[i].append(random.randint(n // 2, n * 2) * 10**(-2))

        return matrix

    @staticmethod
    def fill_symmetrical_da(n: int):
        matrix = SquareMatrix.of_size(n)
        for i in range(n):
            for j in range(i, n):
                if i == j:
                    matrix[i][j] = random.randint(n // 2, n * 2) * 10 ** 2
                else:
                    value = random.randint(n // 2, n * 2) * 10 ** (-2)
                    matrix[i][j] = matrix[j][i] = value

        return matrix
