package cg.iseepublish.test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

/*
 * A typical way to implement the testcase
 */
public class TypicalTestCase implements TestCase
{
    private List< Method > executeMethodList = null;
    public TypicalTestCase()
    {
    }
    
    /*
     * this constructor give the user the opportunity to arrange the call sequence
     */
    public TypicalTestCase( List< String > methodNameList )
    {
        this( methodNameList, false );
    }
    
    public TypicalTestCase( List< String > methodNameList, boolean strickCheck )
    {
        createExecuteMethodList( methodNameList, strickCheck );
    }
    
    /*
     * execute methods which de
     * @see cg.iseepublish.test.TestCase#execute()
     */
    public void execute()
    {
        List< Method > executeMethods = ( executeMethodList == null ) ? createDefaultMethodList() : executeMethodList;
        if( executeMethods != null || executeMethods.size() == 0 )
        {
            return;
        }
        
        for( Method m : executeMethods )
        {
            Class<?>[] params = m.getParameterTypes();
            if( params != null && params.length != 0 )
            {
                // test case method SHOULD NOT have parameters, ignore it
                System.out.printf( "Ignore invalid test method %s: SHOULD NOT have parameters", m );
                continue;
            }
            
            try 
            {
                m.invoke( null );
            } 
            catch( Throwable ex ) 
            {
                System.out.printf( "Test %s failed: %s %n", m, ex.getCause() );
            }
        }
    }
    
    /*
     * FIXME: think over exception handling later
     */
    protected void createExecuteMethodList( List< String > methodNameList, boolean strickCheck )
    {
        if( methodNameList == null || methodNameList.size() == 0 )
            return;
        
        createExecuteMethodList();
        for( String methodName : methodNameList )
        {
            if( methodName == null || methodName.length() == 0 )
            {
                if( strickCheck )
                    throw new IllegalArgumentException( "Invalid method name: " + methodName );
                else
                    continue;
            }
            
            try
            {
                Method m = getClass().getMethod( methodName, null );
                if( m == null )
                {
                    if( strickCheck )
                        throw new IllegalArgumentException( "Not found method name: " + methodName );
                    else
                        continue;
                }
                
                executeMethodList.add( m );
            }
            catch( Exception e )
            {
                if( strickCheck )
                    throw new IllegalArgumentException( e.toString() );
                else
                    continue;
            }
            
        }
    }
    
    protected void createExecuteMethodList()
    {
        if( executeMethodList != null )
            return;
        executeMethodList = new ArrayList< Method >();
    }
    
    protected List< Method > createDefaultMethodList()
    {
        List< Method > defaultMethods = new ArrayList< Method >();
        for( Method m : this.getClass().getMethods() ) 
        {
            if( m.isAnnotationPresent( Test.class ) && !m.getName().equals( "execute" ) ) 
            {
                defaultMethods.add( m );
            }
        }
        return defaultMethods;
    }
}