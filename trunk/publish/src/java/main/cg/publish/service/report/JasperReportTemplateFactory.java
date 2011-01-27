/*
 * This class provides methods to create the template of JasperReport
 * OUTPUT: the instance of JasperReport ( haven't filled data yet ) 
 */
package cg.publish.service.report;


import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JRException;


public interface JasperReportTemplateFactory
{
    public JasperReport createReportTemplate( ReportInfo reportInfo )  throws JRException;
}