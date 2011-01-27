package cg.iseepublish.test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;

/*
 * A typical way to implement the testcase
 */
public class TypicalCompositeTestCase extends TypicalTestCase implements CompositeTestCase
{
    protected List< TestCase > subCaseList = null;
    
    public void addSubCase( TestCase testCase )
    {
        createSubCaseList();
        subCaseList.add( testCase );
    }
    
    public void setSubCaseList( List< TestCase > subCaseList )
    {
        this.subCaseList = subCaseList;
    }
    
    protected void createSubCaseList()
    {
        if( subCaseList != null )
            return;
        subCaseList = new ArrayList< TestCase >();
    }
}