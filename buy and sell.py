p=[7,1,5,3,6,4]
o=min(p)
a=p.index(o)
b=(p[a:])
print(b)
c=max(b)
profit=c-o
if profit>0:
    print(profit)
else:
    print("0")
    


               
