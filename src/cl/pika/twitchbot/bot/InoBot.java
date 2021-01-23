package cl.pika.twitchbot.bot;

import java.util.HashMap;

import cl.pika.twitchbot.util.ICommand;
import cl.pika.twitchbot.util.UserCategory;
import cl.pika.twitchbot.util.CmdCategory;

//////////////////////////////
//      ♡ InoBot ♡
//  Autor:      Pika
//  Creado:     15-Enero-2021
//  Modificado: 15-Enero-2021
//  Bot dedicado especificamente al stream
//  de Inosagi, configurado para ello y todas sus necesidades ♡
//////////////////////////////
public class InoBot extends TwitchBot {
    private static String BOT_VERSION = "v0.2";

    //////////////////////////////
    //  ♡ CONFIGURACIONES BASICAS
    //////////////////////////////
    public String getPrefix() { return _prefix; }
    private HashMap<String, ICommand> commands = new HashMap<>();
    public InoBot(String nombre, String password, String channel, String prefix, boolean[] logs) {
        super(nombre, password, channel, logs);
        super._prefix = prefix;
        System.out.println("Inobot Version: " + BOT_VERSION);
        addTextCommands();
    }

    /**
     * Metodo que funciona como un despachador de comandos, verifica primero
     * que es un comando que el bot debe leer (Mediante el prefijo configurado)
     * y de ser así, envía el mensaje al parseador correspondiente
     */
    @Override
    public void handleChatMessage(String user, String msg, String tags){
        super.handleChatMessage(user, msg, tags);
        if (msg.startsWith(_prefix)){
            boolean mod = checkModOrStreamer(tags);
            log("*** Handleling Message: " + msg + " Mod?: " + mod);
            handleCommand(user, msg.trim(), tags);
        }
    }

    /**
     * Metodo que dispacha los comandos del bot, parsea el comando adecuado y de
     * existir, llama a la ejecución que corresponde.
     */
    private void handleCommand(String user, String msg, String tags){
        int spaceIndex = msg.indexOf(" ");
        String _command = (spaceIndex != -1) ? msg.substring(0, spaceIndex) : msg;
        if (commands.containsKey(_command)){
            commands.get(_command).execute(user, msg, tags);
        }
    }

    /**
     * Metodo que añade comandos de texto al bot.
     */
    private void addTextCommands() {
        /**
         * >comic
         * Link al comic que está trabajando ahora
         */
        commands.put(_prefix + "comic", new ICommand(){
            @Override public CmdCategory getCategory() { return CmdCategory.GENERAL; }
            @Override public UserCategory getUser() { return UserCategory.USER; }
            @Override public String getDescripcion() { return ""; }
            
            @Override public void execute(String user, String msg, String tags) {
                String _msg = "";
                _msg += "¡Lee el comic en Webtoon aquí! http://bit.ly/hainu-comic";
                sendToChat(_msg);
            }
        });
        
        /**
         * >hainu / hassi
         * Link a la pagina de los Hainu Assistants
         */
        ICommand hainu = new ICommand(){
            @Override public CmdCategory getCategory() { return CmdCategory.GENERAL; }
            @Override public UserCategory getUser() { return UserCategory.USER; }
            @Override public String getDescripcion() { return ""; }
            
            @Override public void execute(String user, String msg, String tags) {
                String _msg = "";
                _msg += "Aprende más sobre los Hainu Assistants aquí: https://www.hainucompany.com/";
                sendToChat(_msg);
            }
        };
        commands.put(_prefix + "hainu", hainu);
        commands.put(_prefix + "hassi", hainu);
        
        /**
         * >patreon
         * Link al patreon de la furra
         */
        commands.put(_prefix + "patreon", new ICommand(){
            @Override public CmdCategory getCategory() { return CmdCategory.GENERAL; }
            @Override public UserCategory getUser() { return UserCategory.USER; }
            @Override public String getDescripcion() { return ""; }
            
            @Override public void execute(String user, String msg, String tags) {
                String _msg = "";
                _msg += "Te agradeceria si me apoyas en mi Patreon <3 https://www.patreon.com/inosagi";
                sendToChat(_msg);
            }
        });
        
        /**
         * >kofi / coffee / cafe
         * Link al kofi para donaciones
         */
        ICommand kofi = new ICommand(){
            @Override public CmdCategory getCategory() { return CmdCategory.GENERAL; }
            @Override public UserCategory getUser() { return UserCategory.USER; }
            @Override public String getDescripcion() { return ""; }
            
            @Override public void execute(String user, String msg, String tags) {
                String _msg = "";
                _msg += "¡Puedes apoyarme con un cafesito! https://ko-fi.com/inosagi";
                sendToChat(_msg);
            }
        };
        commands.put(_prefix + "kofi", kofi);
        commands.put(_prefix + "coffee", kofi);
        commands.put(_prefix + "cafe", kofi);
        
        /**
         * >ig / instagram
         * Muestra el instagram de Inosagi
         */
        ICommand ig = new ICommand(){
            @Override public CmdCategory getCategory() { return CmdCategory.GENERAL; }
            @Override public UserCategory getUser() { return UserCategory.USER; }
            @Override public String getDescripcion() { return ""; }
            
            @Override public void execute(String user, String msg, String tags) {
                String _msg = "";
                _msg += "Puedes ver mi arte en instagram <3 https://www.instagram.com/inosagi_art/";
                sendToChat(_msg);
            }
        };

        commands.put(_prefix + "ig", ig);
        commands.put(_prefix + "instagram", ig);
        
        /**
         * >fa / furaffinity
         * Link al furaffinity de la furra
         */
        // commands.put(_prefix + "fa", new ICommand(){
        //     @Override public CmdCategory getCategory() { return CmdCategory.GENERAL; }
        //     @Override public UserCategory getUser() { return UserCategory.USER; }
        //     @Override public String getDescripcion() { return ""; }
            
        //     @Override public void execute(String user, String msg, String tags) {
        //         String _msg = "";
        //         _msg += "Mira las furradas en mi pagina furra. https://www.furaffinity.net/user/inosagi/";
        //         sendToChat(_msg);
        //     }
        // });
        
        /**
         * >da / deviantart
         * Link al deviantart de la furra
         */
        ICommand da = new ICommand(){
            @Override public CmdCategory getCategory() { return CmdCategory.GENERAL; }
            @Override public UserCategory getUser() { return UserCategory.USER; }
            @Override public String getDescripcion() { return ""; }
            
            @Override public void execute(String user, String msg, String tags) {
                String _msg = "";
                _msg += "¡Visita mi DeviantArt! https://www.deviantart.com/inosagi";
                sendToChat(_msg);
            }
        };

        commands.put(_prefix + "da", da);
        commands.put(_prefix + "deviantart", da);
        
        /**
         * >twitter
         * Link al twitter de la furra
         */
        // commands.put(_prefix + "twitter", new ICommand(){
        //     @Override public CmdCategory getCategory() { return CmdCategory.GENERAL; }
        //     @Override public UserCategory getUser() { return UserCategory.USER; }
        //     @Override public String getDescripcion() { return ""; }
            
        //     @Override public void execute(String user, String msg, String tags) {
        //         String _msg = "";
        //         _msg += "¡Sigueme en Twitter! <3 https://twitter.com/inosagi";
        //         sendToChat(_msg);
        //     }
        // });
        
        /**
         * >discord / dc
         * Link al discord de la furra
         */
        ICommand dc = new ICommand(){
            @Override public CmdCategory getCategory() { return CmdCategory.GENERAL; }
            @Override public UserCategory getUser() { return UserCategory.USER; }
            @Override public String getDescripcion() { return ""; }
            
            @Override public void execute(String user, String msg, String tags) {
                String _msg = "";
                _msg += "¡Unete a nuestra comunidad! :D https://discord.gg/8vpvCHG";
                sendToChat(_msg);
            }
        };

        commands.put(_prefix + "dc", dc);
        commands.put(_prefix + "discord", dc);

        /**
         * >tablet / specs / tool
         * Menciona la tablet que utiliza la furra
         */
        ICommand tablet = new ICommand(){
            @Override public CmdCategory getCategory() { return CmdCategory.GENERAL; }
            @Override public UserCategory getUser() { return UserCategory.USER; }
            @Override public String getDescripcion() { return ""; }
            
            @Override public void execute(String user, String msg, String tags) {
                String _msg = "";
                _msg += "¡Dibujo desde mi pc utilizando una Tableta Wacom Intuos Pro!";
                sendToChat(_msg);
            }
        };
        commands.put(_prefix + "tablet", tablet);
        commands.put(_prefix + "specs", tablet);
        commands.put(_prefix + "tool", tablet);
    }
}