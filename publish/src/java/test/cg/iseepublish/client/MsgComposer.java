
package cg.iseepublish.client;

/*
 * very simplified implementation
 */
public class MsgComposer
{
    private static final String PART1 = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><MOSMS appId=\"1\" username=\"testuser\" password=\"test123\"><MESSAGE msisdn=\"";
    private static final String PART2 = "\" source=\"44444\" tid=\"132534\">";
    private static final String PART3 = "</MESSAGE></MOSMS>";
    
    String composeRequest( String msisdn, String action )
    {
        return PART1 + msisdn + PART2 + action + PART3;
    }
}

