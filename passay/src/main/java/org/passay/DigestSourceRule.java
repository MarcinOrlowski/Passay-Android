/* See LICENSE for licensing and NOTICE for copyright. */
package org.passay;

import org.cryptacular.bean.EncodingHashBean;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Rule for determining if a password matches a digested password from a different source. Useful for when separate
 * systems cannot have matching passwords. If no password reference has been set that matches the label on the rule,
 * then passwords will meet this rule. See {@link PasswordData#setPasswordReferences(List)}
 *
 * @author  Middleware Services
 */
public class DigestSourceRule extends SourceRule
{

  /** Hash bean to use for comparing hashed passwords. */
  private final EncodingHashBean hashBean;

  /** Character set to use for undigested passwords. */
  private Charset charset = StandardCharsets.UTF_8;


  /**
   * Creates new digest source rule which operates on password references with the supplied label.
   *
   * @param  bean  encoding hash bean
   */
  public DigestSourceRule(final EncodingHashBean bean)
  {
    hashBean = bean;
  }


  /**
   * Sets the character set to use for undigested passwords.
   *
   * @param  set  to use for undigested passwords
   */
  public void setCharset(final Charset set)
  {
    if (set == null) {
      throw new NullPointerException("Character set cannot be null");
    }
    charset = set;
  }


  /**
   * Determines whether a digested password matches a reference value.
   *
   * @param  undigested  candidate clear text password.
   * @param  reference  reference digested password.
   *
   * @return  true if passwords match, false otherwise.
   */
  @Override
  protected boolean matches(final String undigested, final PasswordData.Reference reference)
  {
    return hashBean.compare(reference.getPassword(), undigested.getBytes(charset));
  }
}
