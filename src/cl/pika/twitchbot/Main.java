package cl.pika.twitchbot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import cl.pika.twitchbot.util.BotTarget;
import cl.pika.twitchbot.bot.GalaBot;
import cl.pika.twitchbot.bot.InoBot;
import cl.pika.twitchbot.bot.TwitchBot;

/**
 * TODO
 * ♡ Cola para jugar con Gala (Prioridad para Subs) (1-1?)
 * ♡ Implementar interface de opciones (Con que cuenta entrar, que prefijo usar)
 * ♡ Implementar interface de detalles (Modos actuales, cuantos han votado)
 * 
 * POSIBLES
 * ♡ Implementación con Discord?
 * ♡ Creación de Bot en Discord?
 * ♡ Externalizar los textos a archivo de texto plano
 * ♡ Leer comandos de un archivo -> Creación de comandos personalizados en runtime
 */

//////////////////////////////
//      ♡ Main ♡
//  Autor:      Pika
//  Creado:     08-Julio-2018
//  Modificado: 29-Enero-2021
//
//  Bot para mis amigues ♡
//////////////////////////////
public class Main {   
    private static String _nombre;
    private static String _password;
    private static String _channel;
    private static String _prefix;
    private static final boolean[] _logs = new boolean[4];

    private static final HashMap<String, String> oathkeys = new HashMap<>();

    private static BotTarget target = BotTarget.BASE;
    private static TwitchBot bot;
    
    public static void main(String[] args) {
        loadOathKeys();
        handleArgs(args);
        System.out.println("Nombre: " + _nombre);
        System.out.println("Pass: " + _password);
        System.out.println("Channel: " + _channel);
        System.out.println("Prefix: " + _prefix);
        System.out.println("verbose, msg, chat, cmd");
        System.out.println(Arrays.toString(_logs));

        switch(target){
            case GALAXIAS:
                bot = new GalaBot(_nombre, _password, _channel, _prefix, _logs);
                bot.sendToChat("¡Estoy listo para trabajar ♡! Usa " + _prefix + "help para saber más de mi.");
                break;
            case INOSAGI:
                bot = new InoBot(_nombre, _password, _channel, _prefix, _logs);
                bot.sendToChat("¡Estoy lista para trabajar ♡!");    
                break;
            case BASE:
                bot = new TwitchBot(_nombre, _password, _channel, _logs);
                bot.sendToChat("¡Estoy listo para trabajar ♡!");    
                break;
        }


        /**
        * Añadir Handler para "Ctrl+C" en la terminal, de manera
        * que el bot se cierre de buena manera al cerrar el programa
        * de manera forzada.
        */
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override public void run() {
                try {
                    Thread.sleep(200);
                    bot.sendToChat("Gracias por llamarme, ¡nos vemos!");
                    bot.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public static void disconnect(){
        if (bot != null){
            bot.sendToChat("Gracias por llamarme, ¡nos vemos!");
            bot.close();
        }
    }

    /**
     * Carga las keys necesarias para que el bot pueda identificarse
     * de buena manera en los canales de Twitch
     */
    private static void loadOathKeys(){
        try {
//            Scanner sc = new Scanner(new File("resources/oath.keys"));
            Scanner sc = new Scanner(Main.class.getResourceAsStream("/resources/oath.keys"));
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String k = line.substring(0, line.indexOf("|"));
                String v = line.substring(line.indexOf("|")+1) + "\r\n";
                oathkeys.put(k, v);
            }
        } catch (Exception e) {
            System.out.println("No key has been found, closing the program...");
            System.exit(1);
        }
    }

   
    //////////////////////////////
    //  ♡ ARGS ♡
    //  0 => Default ()
    //  -v   / +v        | verbose on/off
    //  -msg / +msg      | msgLog  on/off
    //  -cht / +cht      | chatLog on/off
    //  -cmd / +cmd      | cmdLog  on/off
    //  -n <nick>        | nombre
    //  -u paxn/bot/oath | password (Paxncho | PenchoBot)
    //  -c <channel>     | channel
    //  -p <prefix>      | prefix
    //  -gala / galaxias | Default Galaxias
    //  -ino / inosagi   | Default Inosagi
    private static void handleArgs(String[] args){
        if (args.length < 1) {      // No args
            System.out.println("No arguments, closing the program...");
            System.exit(2);
        }

        for (int i = 0; i < args.length; i++) {
            switch(args[i].substring(1)){
                case "gala":
                case "galaxias": defaultGalaxias(); break;
                case "ino":
                case "inosagi": defaultInosagi();   break;
                case "v":   _logs[0] = args[i].startsWith("-");  break;
                case "msg": _logs[1] = args[i].startsWith("-");  break;
                case "cht": _logs[2] = args[i].startsWith("-");  break;
                case "cmd": _logs[3] = args[i].startsWith("-");  break;
                case "n":   _nombre = args[i+1];     i++;        break;
                case "c":   _channel = args[i+1];    i++;        break;
                case "p":   _prefix = args[i+1];     i++;        break;
                case "u":
                    if (oathkeys.containsKey(args[i+1].toLowerCase()))
                        _password = oathkeys.get(args[i+1].toLowerCase());
                    else
                        _password = args[i+1];
                    break;
            }
        }
    }

    private static void defaultGalaxias(){
        //////////////////////////////
        // ♡ DEFAULTS
        _nombre = "LaxyBot";
        _password = oathkeys.get("laxybot");
        _channel = "galaxias";
        _prefix = "!";
        _logs[0] = true;
        _logs[1] = false;
        _logs[2] = false;
        _logs[3] = false;
        target = BotTarget.GALAXIAS;
        //////////////////////////////
    }

    private static void defaultInosagi(){
        //////////////////////////////
        // ♡ DEFAULTS
        _nombre = "InosagiBot";
        _password = oathkeys.get("inosagibot");
        _channel = "inosagi";
        _prefix = "!";
        _logs[0] = true;
        _logs[1] = false;
        _logs[2] = false;
        _logs[3] = false;
        target = BotTarget.INOSAGI;
        //////////////////////////////
    }
    //////////////////////////////
}

