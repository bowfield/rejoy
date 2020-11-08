import socket, threading
import keyboard

users = 0

def user(conn, n):
    global users

    myid = users
    print("New user: " + str(myid))
    users += 1

    while True:
        data = conn.recv(128)

        if not data:
            break

        s = data.decode().split(';') # UP/DOWN;keycode

        print(s[0] + ' by ' + s[1])
        if s[0] == 'DOWN':
            keyboard.press(s[1])
        if s[0] == 'UP':
            keyboard.release(s[1])

    print("User disconnect: " + str(myid))
    users -= 1


myaddr = socket.gethostbyname_ex(socket.gethostname())[-1]
print(str(myaddr))

sock = socket.socket()
sock.bind(('', 9090))
sock.listen(10)

while True:
    conn, addr = sock.accept()
    threading.Thread(target=user, args=(conn, 0)).start()