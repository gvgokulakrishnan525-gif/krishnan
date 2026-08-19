from tkinter import*
from tkinter import messagebox
import random
import smtplib
import sqlite3
con=sqlite3.connect("gokul.db")
cur=con.cursor()
a=Tk()
g=str(random.randint(1000,9999))
def log():
    if(e.get()=="admin") and (e1.get()=="12345"):
        messagebox.showinfo("success","username and password correct")
    else:
        messagebox.showinfo("error")
def reg():
    w=Tk()
    def verify():
        if (g==e5.get()):
            messagebox.showinfo("message","otp is verified")
        else:
            messagebox.showinfo("error","invalid otp")
    def otp():
        email=e2.get()
        sender_email="gvgokulakrishnan525@gmail.com"
        sender_password="qvpp jyoj mppn smwf"
        rec_email=e2.get()
        server=smtplib.SMTP("smtp.gmail.com",587)
        server.starttls()
        server.login(sender_email,sender_password)
        msg="you OTP is{}".format(g)
        server.sendmail(sender_email,rec_email,msg)
        server.quit()
        get_otp=e5.get()
    def save():
        cur.execute("DROP TABLE IF EXISTS t1")
        qur="create table if not exists t1(email text,password text,confirm_password text,enter_otp text)"
        cur.execute(qur)
        x=e2.get()
        x1=e3.get()
        x2=e4.get()
        x3=e5.get()
        data=(x,x1,x2,x3,)
        qur1="insert into t1(email,password,confirm_password,enter_otp) values(?,?,?,?)"
        cur.execute(qur1,data)
        con.commit()
        d="select* from t1"
        cur.execute(d)
        f=cur.fetchall()
        print(f)
        
    w.geometry('500x400')
    w.config(bg="brown")
    l2=Label(w,text="email")
    l2.place(x=60,y=100)
    e2=Entry(w)
    e2.place(x=200,y=100)
    l3=Label(w,text="password",bg="deep pink",font=("times new roman",12))
    l3.place(x=60,y=150)
    e3=Entry(w)
    e3.place(x=200,y=150)
    l4=Label(w,text="confirm password")
    l4.place(x=60,y=200)
    e4=Entry(w)
    e4.place(x=200,y=200)
    l5=Label(w,text="enter OTP")
    l5.place(x=60,y=250)
    e5=Entry(w)
    e5.place(x=200,y=250)
    b3=Button(w,text="send OTP",command=otp)
    b3.place(x=130,y=300)
    b4 =Button(w,text="verify OTP",command=verify)
    b4.place(x=250,y=300)
    b5=Button(w,text="save",command=save)
    b5.place(x=200,y=350)
def clr():
    e.delete(0,END)
    e1.delete(0,END)
a.geometry("335x597")
pi=PhotoImage(file="nature.png")
l6=Label(a,image=pi)
l6.pack()
l=Label(a,text="username")
l.place(x=50,y=100)
e=Entry()
e.place(x=150,y=100)
l1=Label(a,text="password")
l1.place(x=50,y=150)
e1=Entry()
e1.place(x=150,y=150)
b=Button(a,text="register",command=reg)
b.place(x=50,y=200)
b1=Button(a,text="login",command=log)
b1.place(x=120,y=200)
b2=Button(a,text="clear",command=clr)
b2.place(x=180,y=200)


