// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   FileSplitter.java

package com.wol3.client.forms;

import com.wol3.client.Settings;
import com.wol3.client.data.InvalidTimeException;
import com.wol3.util.TimestampParser;
import java.awt.Frame;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

// Referenced classes of package com.wol3.client.forms:
//            OpenFileOrCombatLogForm, ParseProgressDialog, FastZipOutputStream

public class FileSplitter
{

    public FileSplitter()
    {
        ttc = new TimestampParser();
        df = new SimpleDateFormat("yyMMdd HHmmss");
        logger = java.util.logging.Logger.getLogger(getClass().getName());
        utf8 = java.nio.charset.Charset.forName("UTF8");
    }

    public static void main(java.lang.String args[])
        throws java.lang.Exception
    {
        (new FileSplitter()).run(null);
    }

    public void run(java.awt.Frame parent)
        throws java.lang.Exception
    {
        java.io.File logsDir = com.wol3.client.Settings.instance.getWoWLogsDir();
        javax.swing.JFileChooser jfc;
        if (logsDir != null)
        {
            jfc = new JFileChooser(logsDir);
            java.io.File log = new File((new StringBuilder()).append(logsDir.getAbsoluteFile()).append(java.io.File.separator).append("WoWCombatLog.txt").toString());
            if (log.exists())
                jfc.setSelectedFile(log);
        } else
        {
            jfc = new JFileChooser();
        }
        jfc.setFileFilter(com.wol3.client.forms.OpenFileOrCombatLogForm.getFileFilter());
        int rs = jfc.showOpenDialog(parent);
        if (rs == 0 && jfc.getSelectedFile() != null)
            splitWithProgress(parent, jfc.getSelectedFile());
    }

    public void splitWithProgress(final java.awt.Frame parent, final java.io.File file)
    {
        pd = new com.wol3.client.forms.ParseProgressDialog(parent) {

            public int getProgress()
            {
                try
                {
                    if (channel != null && channel.isOpen())
                    {
                        double ratio = channel.position();
                        ratio /= channel.size();
                        return (int)(ratio * 1000D);
                    }
                }
                catch (java.io.IOException e)
                {
                    e.printStackTrace();
                }
                return 0;
            }

            private static final long serialVersionUID = 1L;
            final com.wol3.client.forms.FileSplitter this$0;

            
            {
                _fld0 = com.wol3.client.forms.FileSplitter.this;
                super($anonymous0);
            }
        }
;
        pd.startUpdateThread();
        pd.setVisible(true);
        java.lang.Runnable r = new java.lang.Runnable() {

            public void run()
            {
                try
                {
                    split(file);
                }
                catch (java.io.IOException e)
                {
                    e.printStackTrace();
                }
                try
                {
                    javax.swing.SwingUtilities.invokeAndWait(new java.lang.Runnable() {

                        public void run()
                        {
                            pd.stopUpdateThread();
                            pd.setVisible(false);
                            pd.dispose();
                        }

                        final com.wol3.client.forms._cls2 this$1;

                    
                    {
                        _fld1 = com.wol3.client.forms._cls2.this;
                        super();
                    }
                    }
);
                }
                catch (java.lang.InterruptedException e)
                {
                    e.printStackTrace();
                }
                catch (java.lang.reflect.InvocationTargetException e)
                {
                    e.printStackTrace();
                }
                int rs = javax.swing.JOptionPane.showConfirmDialog(parent, "Do you want to delete the original file?", "WoL Client: Auto archive", 0);
                if (rs == 0 && !file.delete())
                    javax.swing.JOptionPane.showMessageDialog(parent, (new StringBuilder("Failed to delete ")).append(file).toString());
            }

            final com.wol3.client.forms.FileSplitter this$0;
            private final java.io.File val$file;
            private final java.awt.Frame val$parent;


            
            {
                _fld0 = com.wol3.client.forms.FileSplitter.this;
                file = file1;
                parent = frame;
                super();
            }
        }
;
        java.lang.Thread t = new Thread(r);
        t.start();
    }

    public void split(java.io.File selectedFile)
        throws java.io.IOException
    {
        long start = java.lang.System.currentTimeMillis();
        java.io.FileInputStream fis = new FileInputStream(selectedFile);
        java.io.BufferedInputStream bis = new BufferedInputStream(fis, 0x40000);
        java.io.BufferedReader in = new BufferedReader(new InputStreamReader(bis, utf8), 4096);
        channel = fis.getChannel();
        long lastTimestamp = 0L;
        java.io.OutputStreamWriter out = null;
        java.io.File folder = selectedFile.getParentFile();
        java.lang.String line;
        for (int lineNumber = 1; (line = in.readLine()) != null; lineNumber++)
        {
            lineNumber++;
            int splitIdx = line.indexOf("  ");
            if (splitIdx != -1)
            {
                java.lang.String time = line.substring(0, splitIdx);
                long timestamp = ttc.parseSlow(time);
                if (timestamp == -1L)
                {
                    (new InvalidTimeException(lineNumber, (new StringBuilder("Invalid date on line ")).append(lineNumber).append(": ").append(time).toString())).printStackTrace();
                } else
                {
                    if (out == null)
                        out = initIO(folder, null, timestamp);
                    else
                    if (java.lang.Math.abs(timestamp - lastTimestamp) > 0x124f80L)
                        out = initIO(folder, out, timestamp);
                    out.write(line);
                    out.write("\r\n");
                    lastTimestamp = timestamp;
                }
            }
        }

        if (out != null)
            out.close();
        in.close();
        logger.info((new StringBuilder("Done in ")).append(java.lang.System.currentTimeMillis() - start).append(" ms").toString());
    }

    private java.io.OutputStreamWriter initIO(java.io.File folder, java.io.OutputStreamWriter old, long timestamp)
        throws java.io.IOException
    {
        if (old != null)
            old.close();
        folder = new File((new StringBuilder()).append(folder.getAbsoluteFile()).append(java.io.File.separator).append("archive").toString());
        if (!folder.exists())
            folder.mkdir();
        java.lang.String date = df.format(new Date(timestamp));
        java.lang.String outfile = (new StringBuilder(java.lang.String.valueOf(folder.getAbsolutePath()))).append(java.io.File.separator).append(date).append(".zip").toString();
        logger.info((new StringBuilder("Created ")).append(outfile).toString());
        com.wol3.client.forms.FastZipOutputStream zos = new FastZipOutputStream(new BufferedOutputStream(new FileOutputStream(outfile), 8192));
        zos.setLevel(7);
        zos.putNextEntry(new ZipEntry((new StringBuilder(java.lang.String.valueOf(date))).append(".txt").toString()));
        return new OutputStreamWriter(new BufferedOutputStream(zos, 4096), utf8);
    }

    private com.wol3.util.TimestampParser ttc;
    private java.nio.channels.FileChannel channel;
    private com.wol3.client.forms.ParseProgressDialog pd;
    java.text.DateFormat df;
    java.util.logging.Logger logger;
    java.nio.charset.Charset utf8;


}
