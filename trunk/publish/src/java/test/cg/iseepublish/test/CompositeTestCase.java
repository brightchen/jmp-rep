package cg.iseepublish.test;

import java.util.List;

public interface CompositeTestCase extends TestCase
{
    public void addSubCase( TestCase testCase );
    public void setSubCaseList( List< TestCase > subCaseList );
}