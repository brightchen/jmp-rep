package cg.publish.service.report;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cg.common.logging.Logger;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/*
 * This class create the jasper report
 */
public class JasperReportService extends AbstractReportService
{
  private static final Logger log = Logger.getLogger( JasperReportService.class );

  private JasperReportTemplateFactory jasperReportTemplateFactory;
  private DataSourceFactory dataSourceFactory;
  private String csvDelimiter = null;

  public JasperReportService()
  {
    // use IsepublishReportContentFilter;
    // setReportContentFilter( new IseepublishReportContentFilter() );
  }

  protected byte[] generateReportContent( ReportInfo reportInfo ) throws JRException
  {
    log.debug( "going to create datasource." );
    // JRDataSource datasource = createJRDataSource( reportInfo );
    List< ? > datasource = createListDataSource( reportInfo );
    if ( datasource == null )
    {
      log.warn( "datasource is null." );
      // create empty report even if it is null
    }
    log.debug( "datasource created." );

    // set the datasouce in case it is required when create template factory.
    reportInfo.setDataSource( datasource );

    // log.debug( "going to compile report template." );
    // JasperReport jasperReport = JasperCompileManager.compileReport( rawTemplateFileUrl );
    JasperReportTemplateFactory rtFactory = getReportTemplateFactory( reportInfo.getReportType() );
    if ( rtFactory == null )
    {
      log.error( "The JasperReportTemplateFactory is null, check the configuration." );
      return null;
    }

    JasperReport jasperReport = rtFactory.createReportTemplate( reportInfo );

    Map< String, String > params = new HashMap< String, String >();
    params.put( "startDate", DateUtil.getDateString( reportInfo.getStartDate() ) );
    params.put( "endDate", DateUtil.getDateString( reportInfo.getEndDate() ) );
    params.put( "generatedTime", DateUtil.getGeneratedOn() );
    // if( reportInfo instanceof EndUserReportInfo )
    // {
    // log.debug( "This is a end-user_report. getting the summary data." );
    // EndUserReportInfo euri = ( EndUserReportInfo )reportInfo;
    // params.put( "totalUniqueUserNum", String.valueOf( euri.getTotalUniqueUserNum() ) );
    // params.put( "totalImpressionNum", String.valueOf( euri.getTotalImpressionNum() ) );
    // }

    JasperPrint jasperPrint = JasperFillManager.fillReport( jasperReport, params,
                                                            new JRBeanCollectionDataSource( datasource ) );

    return generateReportContent( jasperPrint, reportInfo.getReportFormat() );
  }

  /*
   * generate the content of the report
   */
  protected byte[] generateReportContent( JasperPrint jasperPrint, String reportFormat ) throws JRException
  {
    if ( reportFormat == null || reportFormat.length() == 0 )
      reportFormat = ReportFormats.PDF.toString();

    log.debug( "generating report content." );
    byte[] output = null;

    if ( reportFormat.compareTo( ReportFormats.PDF.toString() ) == 0 )
    {
      // response.setHeader("Content-disposition", "inline; filename=report.pdf");
      output = JasperExportManager.exportReportToPdf( jasperPrint );
    }
    else
    {
      JRExporter exporter;

      if ( reportFormat.compareTo( ReportFormats.CSV.toString() ) == 0 )
      {
        // response.setContentType("text/plain");
        exporter = new JRCsvExporter();
      }
      else if ( reportFormat.compareTo( ReportFormats.HTML.toString() ) == 0 )
      {
        exporter = new JRHtmlExporter();
        exporter.setParameter( JRExporterParameter.JASPER_PRINT, jasperPrint );

      }
      else if ( reportFormat.compareTo( ReportFormats.XLS.toString() ) == 0 )
      {
        // response.setContentType("application/vnd.ms-excel");
        exporter = new JRXlsExporter();
      }
      else if ( reportFormat.compareTo( ReportFormats.XML.toString() ) == 0 )
      {
        // response.setContentType("text/xml");
        exporter = new JRXmlExporter();
      }
      else if ( reportFormat.compareTo( ReportFormats.RTF.toString() ) == 0 )
      {
        // response.setContentType("application/rtf");
        exporter = new JRRtfExporter();
      }
      else
      {
        return null;
      }

      output = exportReportToBytes( jasperPrint, exporter );

    }
    return output;
  }

  protected byte[] exportReportToBytes( JasperPrint jasperPrint, JRExporter exporter ) throws JRException
  {
    byte[] output;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    exporter.setParameter( JRExporterParameter.JASPER_PRINT, jasperPrint );
    exporter.setParameter( JRExporterParameter.OUTPUT_STREAM, baos );
    if ( csvDelimiter != null )
    {
      exporter.setParameter( JRCsvExporterParameter.FIELD_DELIMITER, csvDelimiter );
    }

    exporter.exportReport();

    output = baos.toByteArray();

    return output;
  }

  protected JRDataSource createJRDataSource( ReportInfo reportInfo )
  {
    return dataSourceFactory.createJRDataSource( reportInfo );
  }

  protected List< ? > createListDataSource( ReportInfo reportInfo )
  {
    return dataSourceFactory.createListDataSource( reportInfo );
  }

  protected JasperReportTemplateFactory getReportTemplateFactory( ReportType reportType )
  {
    return jasperReportTemplateFactory;
  }

  /***
   * protected JasperReportTemplateFactory getReportTemplateFactory( ReportType reportType ) { if( reportType instanceof
   * GenericImpressionReportType ) return genericImpressionReportTemplateFactory;
   * 
   * return jasperReportTemplateFactory; } /
   ***/
  // dataSourceFactory
  public DataSourceFactory getDataSourceFactory()
  {
    return dataSourceFactory;
  }

  public void setDataSourceFactory( DataSourceFactory dataSourceFactory )
  {
    this.dataSourceFactory = dataSourceFactory;
  }

  // jasperReportTemplateFactory
  public JasperReportTemplateFactory getJasperReportTemplateFactory()
  {
    return jasperReportTemplateFactory;
  }

  public void setJasperReportTemplateFactory( JasperReportTemplateFactory jasperReportTemplateFactory )
  {
    this.jasperReportTemplateFactory = jasperReportTemplateFactory;
  }

  // csvDelimiter
  public String getCsvDelimiter()
  {
    return csvDelimiter;
  }

  public void setCsvDelimiter( String csvDelimiter )
  {
    this.csvDelimiter = csvDelimiter;
  }
}