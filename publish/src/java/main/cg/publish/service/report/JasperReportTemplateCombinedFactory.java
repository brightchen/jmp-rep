/*
 * Based on JasperReportTemplateStaticFactory
 * This class also provider interface to dynamic create report elements.
 *  
 */
package cg.publish.service.report;

import cg.common.logging.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class JasperReportTemplateCombinedFactory extends JasperReportTemplateStaticFactory
{
    public JasperReportTemplateCombinedFactory()
    {
    }
    
    public JasperReport createReportTemplate( ReportInfo reportInfo ) throws JRException
    {
        JasperDesign design = createJasperDesign( reportInfo.getReportType() );
        addDynamicEntities( design, reportInfo );
        return JasperCompileManager.compileReport( design );
    }

    protected JasperDesign createJasperDesign( ReportType reportType )
    {
        //create an empty design or load from file
        String templateFile = getTemplateFilePath( reportType );
        JasperDesign design = null;
        try
        {
            design = JRXmlLoader.load( templateFile );
        }
        catch( JRException jre )
        {
            log.warn( jre.toString() );
        }
        if( design == null )
            design = new JasperDesign();
        
        return design;
    }
    
    /*
     * The sub-classes can overriden this method to add dynamic Jasper entities to the design;
     */
    protected JasperDesign addDynamicEntities( JasperDesign design, ReportInfo reportInfo )
    {
        return design;
    }
    
    private static final Logger log = Logger.getLogger( JasperReportTemplateCombinedFactory.class );
}