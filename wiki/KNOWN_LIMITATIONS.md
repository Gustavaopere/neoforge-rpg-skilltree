# Limitações conhecidas

> **Snapshot documentado:** `main` em 28/08/2026. Esta página registra limitações verificadas do estado atual. Elas não significam que todo o mod esteja quebrado; indicam áreas que a wiki não deve apresentar como mais completas do que realmente são.

## Conteúdo visual não equivale a efeito implementado

A Árvore Principal possui 512 nós, mas parte deles funciona como estrutura, caminho ou requisito. Um ID existir no datapack não garante um modificador numérico próprio.

## Integrações tecnológicas são desiguais

Create, AE2, Oritech e outros provedores tecnológicos aparecem em identidades/especializações. Nem todo caminho possui hook runtime equivalente a integrações profundas como Iron's, Ars, Goety, Malum ou Eidolon.

A wiki não atribui velocidade de máquina, geração de energia ou capacidade de rede sem uma implementação explícita.

## Compendium ainda não está preenchido como enciclopédia completa

A infraestrutura de compendium é ampla, porém o diretório distribuído de entradas contém atualmente apenas `pig.json`. Portanto, não há base para afirmar que todas as classes, perks, mobs e integrações já estejam disponíveis em um compendium in-game completo.

## Bindings de atributos podem falhar quando o atributo não existe

O projeto registra como problema conhecido que alguns efeitos podem apontar para IDs de atributos ausentes/incompatíveis no ambiente atual. O runtime de efeitos pode ignorar bindings não resolvidos em vez de materializar o bônus esperado.

Isso é especialmente importante para interpretar uma perk cuja definição de dados exista mas cujo atributo alvo não esteja disponível na versão/modpack efetivo.

## Alguns IDs vanilla de node effects precisam de reconciliação para 1.21.1

A auditoria consolidada do projeto identifica definições de atributos vanilla usando IDs de linhas posteriores à 1.21.1. Enquanto essa reconciliação não estiver concluída, presença no JSON não deve ser tratada automaticamente como prova de que o modificador entra em jogo.

## Reconcile de nós inválidos requer cuidado

Há um problema conhecido em torno da reconciliação de nós que deixam de ser válidos após mudança de dados. A arquitetura busca preservar estado/migração de forma segura, mas esse caminho ainda precisa de hardening adicional.

## Catálogo do cliente e reload do servidor podem divergir

O servidor possui fluxo de reload de datapack, enquanto partes do cliente ainda podem depender de catálogo/classpath carregado de forma diferente. Isso cria risco de apresentação defasada em cenários de datapack customizado ou reload durante execução.

A autoridade continua sendo o servidor.

## Mutação de XP/mastery pode atualizar mais estado do que o necessário

O projeto registra que caminhos de mudança de XP/mastery podem provocar refresh de atributos e sincronização ampla a cada evento. Funciona como comportamento atual, mas é um ponto de performance/hardening para sessões com muitas awards pequenas.

## Dedupe de procs não é universalmente centralizado

`procDepth` protege várias policies de mastery contra recursão, mas a auditoria do projeto também registra que a deduplicação global de efeitos/procs ainda não está completamente centralizada em uma única autoridade.

## Boss tags e IDs opcionais precisam de atenção

A auditoria consolidada registra problemas de compatibilidade de tag/IDs de boss para 1.21.1 e referências opcionais que podem se tornar obrigatórias dependendo da definição. A wiki, por isso, evita dizer que “todo boss de X mod” é automaticamente reconhecido.

## Testes e CI ainda têm lacunas de hardening

A infraestrutura atual não deve ser descrita como cobertura absoluta. O registro técnico do projeto aponta lacunas como GameTests/integração e verificações de diff/wrapper em determinados fluxos.

## Como esta página deve ser usada

Quando uma página de gameplay e esta página entrarem em conflito, aplique a seguinte ordem de confiança:

1. comportamento observado e código do `main`;
2. dados carregados no `main`;
3. testes atuais;
4. wiki;
5. planos/roadmaps.

Planos nunca transformam sozinhos uma feature em funcionalidade atual.

## Fontes no repositório

- `AGENTS.md`
- `src/main/java/dev/gustavopere/rpgskilltree/core/`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/`
- `src/main/resources/data/rpgskilltree/`
