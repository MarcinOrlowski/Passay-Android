/* See LICENSE for licensing and NOTICE for copyright. */
package org.passay;

import org.passay.entropy.Entropy;
import org.passay.entropy.RandomPasswordEntropyFactory;
import org.passay.entropy.ShannonEntropyFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The central component for evaluating multiple password rules against a candidate password.
 *
 * @author  Middleware Services
 */

public class PasswordValidator implements Rule
{

  /** Password rules. */
  private final List<Rule> passwordRules;

  /** Message resolver. */
  private final MessageResolver messageResolver;


  /**
   * See {@link #PasswordValidator(List)}.
   *
   * @param  rules  to validate
   */
  public PasswordValidator(final Rule... rules)
  {
    this(new PropertiesMessageResolver(), Arrays.asList(rules));
  }


  /**
   * Creates a new password validator with a {@link PropertiesMessageResolver}.
   *
   * @param  rules  to validate
   */
  public PasswordValidator(final List<Rule> rules)
  {
    this(new PropertiesMessageResolver(), rules);
  }


  /**
   * See {@link #PasswordValidator(MessageResolver, List)}.
   *
   * @param  resolver  message resolver.
   * @param  rules  to validate
   */
  public PasswordValidator(final MessageResolver resolver, final Rule... rules)
  {
    this(resolver, Arrays.asList(rules));
  }


  /**
   * Creates a new password validator.
   *
   * @param  resolver  message resolver.
   * @param  rules  to validate
   */
  public PasswordValidator(final MessageResolver resolver, final List<Rule> rules)
  {
    messageResolver = resolver;
    passwordRules = rules;
  }


  /**
   * Validates the supplied password data against the rules in this validator.
   *
   * @param  passwordData  to validate
   *
   * @return  rule result
   */
  @Override
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult(true);
    for (Rule rule : passwordRules) {
      final RuleResult rr = rule.validate(passwordData);
      if (!rr.isValid()) {
        result.setValid(false);
        result.getDetails().addAll(rr.getDetails());
      }
    }
    return result;
  }


  /**
   * Calculates the entropy of the given {@link PasswordData} based on the specified password rules specified. <em>It's
   * important to note that this method does NOT take into account {@link IllegalRegexRule} or {@link AllowedRegexRule}
   * </em> as the regular expressions driving the rules may be negative matches.
   *
   * @param  passwordData  to estimate entropy for
   *
   * @throws  IllegalArgumentException  for unknown {@link PasswordData.Origin} or if the required {@link
   * CharacterRule} instances are unavailable in the {@link #passwordRules} of this validator instance.
   *
   * @see <a href="http://csrc.nist.gov/publications/nistpubs/800-63-1/SP-800-63-1.pdf">PDF Publication</a>
   *
   * @return  entropy estimate
   */
  public double estimateEntropy(final PasswordData passwordData)
  {
    Entropy entropy;
    if (passwordData.getOrigin().equals(PasswordData.Origin.Generated)) {
      entropy = RandomPasswordEntropyFactory.createEntropy(passwordRules, passwordData);
    } else if (passwordData.getOrigin().equals(PasswordData.Origin.User)) {
      entropy = ShannonEntropyFactory.createEntropy(passwordRules, passwordData);
    } else {
      throw new IllegalArgumentException("Unknown password origin: " + passwordData);
    }
    return entropy.estimate();
  }


  /**
   * Returns a list of human-readable messages by iterating over the details in a failed rule result.
   *
   * @param  result  failed rule result.
   *
   * @return  list of human-readable messages describing the reason(s) for validation failure.
   */
  public List<String> getMessages(final RuleResult result)
  {
    final List<String> messages = result.getDetails().stream().map(
      messageResolver::resolve).collect(Collectors.toList());
    return messages;
  }


  @Override
  public String toString()
  {
    return
      String.format(
        "%s@%h::passwordRules=%s,messageResolver=%s",
        getClass().getName(),
        hashCode(),
        passwordRules,
        messageResolver);
  }
}
