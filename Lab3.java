package com.company;
import java.util.*;
import java.util.stream.Collectors;

public class Lab3 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Turnstile turnstile = new Turnstile();
        RegisterSystem regsys = new RegisterSystem();
        do {
            System.out.println("\nВыберите действие:\n1. Создать Ski-Pass\n2. Использовать Ski-Pass\n3. Журнал турникета" +
                    "\n4. Заблокировать Ski-Pass\n5. Выйти");
            switch (regsys.getAnswer(1, 4)) {
                case (1):
                    regsys.CreateSkiPass();
                    break;
                case (2):
                    System.out.print("Ski-Pass id -> ");
                    if(turnstile.skiPassCheck(regsys.register.get(in.nextInt())))
                        System.out.println("Проход разрешён");
                    else
                        System.out.println("Проход запрещён");
                    in.nextLine();
                    break;
                case (3):
                    System.out.println("1. Суммарные данные\n2. Данные для Ski-Pass на будни" +
                            "\n3. Данные для Ski-Pass на выходные\n4. Данные для Ski-Pass на сезон");
                    switch (regsys.getAnswer(1, 4)) {
                        case (1):
                            turnstile.getAllInform();
                            break;
                        case (2):
                            turnstile.getAllInform(1);
                            break;
                        case (3):
                            turnstile.getAllInform(2);
                            break;
                        case (4):
                            turnstile.getAllInform(3);
                            break;
                    }
                    break;
                case (4):
                    System.out.print("Ski-Pass id -> ");
                    regsys.block(in.nextInt());
                    in.nextLine();
                    break;
                case (5):
                    return;
            }
        } while (true);
    }
}

class Turnstile {

    ArrayList<Rec> journal = new ArrayList<>();

    boolean skiPassCheck(SkiPass pass) {
        if (pass == null) {
            return false;
        }
        if (pass.getStatus() == 0) {
            journal.add(new Rec(pass.getId(), pass.type, 0));
            return false;
        }
        if (pass.subtype == 1) {
            if(pass.type == 1 && !isWeekend() && isBetween(pass.startDate, pass.endDate)) {
                journal.add(new Rec(pass.getId(), pass.type, 1));
                return true;
            }
            if (pass.type == 2 && isWeekend() && isBetween(pass.startDate, pass.endDate)) {
                journal.add(new Rec(pass.getId(), pass.type, 1));
                return true;
            }
        }
        if (pass.subtype == 2) {
            if(pass.type == 1 && !isWeekend() && pass.liftsNumber > 0) {
                journal.add(new Rec(pass.getId(), pass.type, 1));
                pass.liftsNumber--;
                return true;
            }
            if (pass.type == 2 && isWeekend() && pass.liftsNumber > 0) {
                journal.add(new Rec(pass.getId(), pass.type, 1));
                pass.liftsNumber--;
                return true;
            }
        }
        if (pass.type == 3 && isBetween(pass.startDate, pass.endDate)) {
            journal.add(new Rec(pass.getId(), pass.type, 1));
            return true;
        }
        else {
            journal.add(new Rec(pass.getId(), pass.type, 0));
        }
        return false;
    }

    private boolean isBetween(GregorianCalendar startDate, GregorianCalendar endDate) {
        GregorianCalendar now = new GregorianCalendar();
        return now.after(startDate) && now.before(endDate);
    }

    private boolean isWeekend() {
        GregorianCalendar now = new GregorianCalendar();
        return now.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || now.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    public void getAllInform() {
        for (Rec i: journal) {
            System.out.println("-------------------------------------------");
            System.out.println("Ski-Pass id: " + i.passId);
            switch (i.passType) {
                case (1):
                    System.out.println("Ski-Pass type: weekdays");
                    break;
                case (2):
                    System.out.println("Ski-Pass type: weekends");
                    break;
                case (3):
                    System.out.println("Ski-Pass type: season");
                    break;
                case (0):
                    System.out.println("Ski-Pass type: blocked");
                    break;
            }
            if (i.permit == 1)
                System.out.println("Access is allowed");
            else
                System.out.println("Access is denied");

        }
    }
    public void getAllInform(int type) {
        for (Rec i: journal) {
            if (i.passType == type) {
                System.out.println("-------------------------------------------");
                System.out.println("Ski-Pass id: " + i.passId);
                switch (i.passType) {
                    case (1):
                        System.out.println("Ski-Pass type: weekdays");
                        break;
                    case (2):
                        System.out.println("Ski-Pass type: weekends");
                        break;
                    case (3):
                        System.out.println("Ski-Pass type: season");
                        break;
                    case (0):
                        System.out.println("Ski-Pass type: blocked");
                        break;
                }
                if (i.permit == 1)
                    System.out.println("Access is allowed");
                else
                    System.out.println("Access is denied");
            }
        }
    }
}

class RegisterSystem {
    Hashtable<Integer, SkiPass> register = new Hashtable<>();
    Scanner in;
    RegisterSystem() {
        in = new Scanner(System.in);
    }

    void CreateSkiPass() {
        int type, subtype = 0, liftsNumber = 0;
        GregorianCalendar startDate, endDate;
        startDate = new GregorianCalendar();
        endDate = new GregorianCalendar();
        System.out.print("Тип SkiPass:\n1. На рабочие дни\n2. На выходные дни\n3. На сезон\n");
        type = getAnswer(1,3);
        if (type < 3) {
            System.out.print("Время действия:\n1. Без учёта количества подъемов\n2. По количеству подъемов\n");
            subtype = getAnswer(1,2);
            if(subtype == 1) {
                int temptype;
                System.out.print("Время действия:\n1. На пол дня с 9:00 до 14:00\n2. На пол дня с 14:00 до 19:00" +
                        "\n3. На день\n4. На 2 дня\n5. На 5 дней\n");
                temptype = getAnswer(1, 5);
                System.out.print("Введите дату начала действия SkiPass (в формате dd-MM-yyyy): ");
                List<Integer> splitDate = Arrays.stream(in.nextLine().split("-")).map(Integer::parseInt).collect(Collectors.toList());
                startDate = new GregorianCalendar(splitDate.get(2), splitDate.get(1) - 1, splitDate.get(0));
                switch (temptype) {
                    case (1):
                        startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH), 9, 0, 0);
                        endDate = new GregorianCalendar(splitDate.get(2), splitDate.get(1) - 1, splitDate.get(0), 13, 0, 0);
                        break;
                    case (2):
                        startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH), 13, 0, 0);
                        endDate = new GregorianCalendar(splitDate.get(2), splitDate.get(1) - 1, splitDate.get(0), 17, 0, 0);
                        break;
                    case (3):
                        endDate = new GregorianCalendar(splitDate.get(2),splitDate.get(1) - 1, splitDate.get(0));
                        endDate.add(Calendar.DAY_OF_MONTH, 1);
                        break;
                    case (4):
                        endDate = new GregorianCalendar(splitDate.get(2), splitDate.get(1) - 1, splitDate.get(0));
                        endDate.add(Calendar.DAY_OF_MONTH, 2);
                        break;
                    case (5):
                        endDate = new GregorianCalendar(splitDate.get(2), splitDate.get(1) - 1, splitDate.get(0));
                        endDate.add(Calendar.DAY_OF_MONTH, 5);
                        break;
                }
            }
            else {
                System.out.print("Количество подъемов:\n1. 4 подъема\n2. 10 подъемов\n3. 20 подъемов\n4. 50 подъемов\n");
                liftsNumber = getAnswer(1, 5);
                switch (liftsNumber) {
                    case (1):
                        liftsNumber = 40;
                        break;
                    case (2):
                        liftsNumber = 10;
                        break;
                    case (3):
                        liftsNumber = 20;
                        break;
                    case (4):
                        liftsNumber = 50;
                        break;
                }
            }
        }
        else {
            System.out.print("Введите дату начала действия SkiPass (в формате dd-MM-yyyy): ");
            List<Integer> splitDate = Arrays.stream(in.nextLine().split("-")).map(Integer::parseInt).collect(Collectors.toList());
            startDate = new GregorianCalendar(splitDate.get(2), splitDate.get(1) - 1, splitDate.get(0));
            endDate = new GregorianCalendar(splitDate.get(2), splitDate.get(1) - 1, splitDate.get(0));
            endDate.add(Calendar.MONTH, 3);
        }
        SkiPass pass = new SkiPass(1, type, subtype, startDate, endDate, liftsNumber);
        register.put(pass.getId(), pass);
    }

    void block(int id) {
        if(this.register.get(id) != null)
            this.register.get(id).changeStatus(0);
        else
            System.out.println("Ski-pass с таким id не существует");
    }

    public int getAnswer(int a, int b) {
        int answer = 0;
        do {
            answer = in.nextInt();
            in.nextLine();
            if (answer < a || answer > b) {
                System.out.print("ОШИБКА! Повторите ввод");
                answer = 0;
            }
        } while (answer == 0);
        return answer;
    }
}

class SkiPass {
    private int id;
    private static int counter = 1;
    private int status;
    int type;
    int subtype;
    GregorianCalendar startDate, endDate;
    int liftsNumber;


    SkiPass (int stat, int typ, int subt, GregorianCalendar stDate, GregorianCalendar enDate, int liftsNum) {
        id = counter++;
        status = stat;
        type = typ;
        subtype = subt;
        startDate = stDate;
        endDate = enDate;
        liftsNumber = liftsNum;
    }

    int getId() {
        return this.id;
    }

    void changeStatus(int num) {
        this.status = num;
    }

    int getStatus() {
        return this.status;
    }
}

class Rec {
    public Rec(int id, int  type, int perm) {
        passId = id;
        passType = type;
        permit = perm;
    }
    int passId;
    int passType;
    int permit;
}