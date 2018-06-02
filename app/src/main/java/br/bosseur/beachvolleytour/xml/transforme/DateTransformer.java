package br.bosseur.beachvolleytour.xml.transforme;

import org.simpleframework.xml.transform.Transform;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

public class DateTransformer implements Transform<Date> {

  private DateFormat df;

  public DateTransformer(String format) {
    df = new SimpleDateFormat(format);
  }

  @Override
  public Date read(String value) throws Exception {
    if( value == null || value.isEmpty()){
      return new Date(0);
    }
    return df.parse(value);
  }

  @Override
  public String write(Date value) throws Exception {
    // We are not writing anything so forget about this.
    return null;
  }
}
