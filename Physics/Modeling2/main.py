from tkinter import *
from formulas import calculate_t, draw_plot
from gifPlayer import play_gif
from math import radians


def check_input_data(H: float, R: float, d: float) -> bool:
    if H <= 0 or R <= 0 or d <= 0:
        return False

    # Не выполняются условие, что скорость в высшей точке >> в нижней
    if d >= 0.2 * R:
        return False

    theta = R / H
    # Значение угла должно располагаться в промежутке [5, 89], иначе
    # будем рассматривать трубу, на которую накладываются другие условия
    if theta < radians(5) or theta > radians(89):
        return False

    return True


def start():
    H = float(ent1.get())
    R = float(ent2.get())
    d = float(ent3.get()) / 10 ** 3
    window.quit()
    if not check_input_data(H, R, d):
        raise ValueError("Введённые данные некорректны")

    theta = R / H
    t = calculate_t(H, theta, d)
    print(t)
    gif_time = 5.94
    draw_plot(t, H, theta)
    play_gif(gif_time / t)



window = Tk()
window.title("Ввод данных")
window.geometry('380x160')

lbl1 = Label(window, text="Высота конуса")
lbl1.grid(column=0, row=0)
lbl2 = Label(window, text="Радиус основания конуса")
lbl2.grid(column=0, row=1)
lbl3 = Label(window, text="Диаметр отверстия")
lbl3.grid(column=0, row=2)

ent1 = Entry(window, width=10)
ent1.grid(column=1, row=0)
ent2 = Entry(window, width=10)
ent2.grid(column=1, row=1)
ent3 = Entry(window, width=10)
ent3.grid(column=1, row=2)

lbl_1 = Label(window, text="м")
lbl_1.grid(column=2, row=0)
lbl_2 = Label(window, text="м")
lbl_2.grid(column=2, row=1)
lbl_3 = Label(window, text="мм")
lbl_3.grid(column=2, row=2)

ent1.insert(1, "1")
ent2.insert(1, "0.268")
ent3.insert(1, "30")

btn = Button(window, text="     Пуск     ", command=start)
btn.grid(column=1, row=6)
window.mainloop()
