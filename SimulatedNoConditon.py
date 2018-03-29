import random

def draw():
    port1 = ['阿联酋','伊朗','澳大利亚','韩国','日本','沙特阿拉伯']
    port2 = ['中国','叙利亚','吉尔吉斯斯坦','黎巴嫩','巴勒斯坦','阿曼']
    port3 = ['乌兹别克斯坦','伊拉克','卡塔尔','越南','印度','朝鲜']
    port4 = ['约旦','泰国','土库曼斯坦','也门','菲律宾','巴林']

    groupA = []
    groupB = []
    groupC = []
    groupD = []
    groupE = []
    groupF = []

    ##一档次
    groupA.append(port1[0])
    port1.remove(port1[0])

    newteam = random.choice(port1)
    groupB.append(newteam)
    port1.remove(newteam)
    num = len(port1)
    newteam = random.choice(port1)
    groupC.append(newteam)
    port1.remove(newteam)
    newteam = random.choice(port1)
    groupD.append(newteam)
    port1.remove(newteam)
    newteam = random.choice(port1)
    groupE.append(newteam)
    port1.remove(newteam)
    groupF.append(port1[0])

    ##二档次
    newteam = random.choice(port2)
    groupA.append(newteam)
    port2.remove(newteam)
    newteam = random.choice(port2)
    groupB.append(newteam)
    port2.remove(newteam)
    newteam = random.choice(port2)
    groupC.append(newteam)
    port2.remove(newteam)
    newteam = random.choice(port2)
    groupD.append(newteam)
    port2.remove(newteam)
    newteam = random.choice(port2)
    groupE.append(newteam)
    port2.remove(newteam)
    groupF.append(port2[0])

    ##三档次
    newteam = random.choice(port3)
    groupA.append(newteam)
    port3.remove(newteam)
    newteam = random.choice(port3)
    groupB.append(newteam)
    port3.remove(newteam)
    newteam = random.choice(port3)
    groupC.append(newteam)
    port3.remove(newteam)
    newteam = random.choice(port3)
    groupD.append(newteam)
    port3.remove(newteam)
    newteam = random.choice(port3)
    groupE.append(newteam)
    port3.remove(newteam)
    groupF.append(port3[0])

    ##四档次
    newteam = random.choice(port4)
    groupA.append(newteam)
    port4.remove(newteam)
    newteam = random.choice(port4)
    groupB.append(newteam)
    port4.remove(newteam)
    newteam = random.choice(port4)
    groupC.append(newteam)
    port4.remove(newteam)
    newteam = random.choice(port4)
    groupD.append(newteam)
    port4.remove(newteam)
    newteam = random.choice(port4)
    groupE.append(newteam)
    port4.remove(newteam)
    groupF.append(port4[0])

    f = open("D://1.txt", "w")
    for i in groupA:
        f.write(i+" ")
    f.write("\n")
    for i in groupB:
        f.write(i+" ")
    f.write("\n")
    for i in groupC:
        f.write(i+" ")
    f.write("\n")
    for i in groupD:
        f.write(i+" ")
    f.write("\n")
    for i in groupE:
        f.write(i+" ")
    f.write("\n")
    for i in groupF:
        f.write(i+" ")
    f.write("\n")
    # 关闭打开的文件
    f.close()
    print(groupA[0],groupA[1],groupA[2],groupA[3])
    print(groupB[0],groupB[1],groupB[2],groupB[3])
    print(groupC[0],groupC[1],groupC[2],groupC[3])
    print(groupD[0],groupD[1],groupD[2],groupD[3])
    print(groupE[0],groupE[1],groupE[2],groupE[3])
    print(groupF[0],groupF[1],groupF[2],groupF[3])

draw()