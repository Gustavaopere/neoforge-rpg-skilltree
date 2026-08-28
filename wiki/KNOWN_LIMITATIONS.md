# Limitações conhecidas

> **Snapshot:** `main`, 28/08/2026. Esta página impede que estrutura, planos ou dados parcialmente ligados sejam descritos como funcionalidade mais completa do que o runtime atual.

## 512 nós não significam 512 efeitos numéricos

Parte da árvore é caminho, gateway, requisito ou estrutura. O catálogo de IDs e o catálogo de efeitos são coisas diferentes.

## Integrações têm profundidades diferentes

Iron's, Ars, Goety, Malum, Eidolon, Epic Fight e morphs possuem hooks/policies concretos em partes relevantes. Caminhos tecnológicos podem existir como identidade antes de haver bônus runtime sobre cada máquina do provider.

## Compendium ainda não está preenchido por completo

O subsistema de compendium é amplo, mas `data/rpgskilltree/compendium/entries/` contém atualmente somente `pig.json`.

## Bindings de atributo podem não materializar o efeito

A auditoria técnica do projeto registra bindings que podem apontar para atributos ausentes/incompatíveis. O runtime pode ignorar um binding não resolvido, então “há um JSON” não é prova suficiente de que o bônus entrou em jogo.

## IDs de atributos vanilla precisam de reconciliação 1.21.1

`AGENTS.md` registra definições de `node_effects` usando IDs de linhas posteriores à 1.21.1. Esses casos devem ser tratados como problema conhecido até reconciliação.

## Reconciliação de nós inválidos ainda precisa de hardening

Mudanças de datapack/provider podem tornar nós persistidos inválidos. O caminho de reconciliação é uma área conhecida que exige cuidado para não perder progresso indevidamente.

## Catálogo do cliente e reload do servidor podem divergir

Partes do cliente ainda podem obter catálogo de forma diferente do reload autoritativo do servidor. Em caso de divergência, o servidor tem prioridade.

## XP/mastery pode provocar refresh amplo

A auditoria atual registra que algumas mutações atualizam/sincronizam mais estado do que seria ideal. É funcional, mas representa ponto de performance para sessões com muitos awards pequenos.

## Dedupe de procs não é universalmente centralizado

`procDepth` protege várias policies, porém a auditoria também registra que a deduplicação global de efeitos/procs ainda não está concentrada em uma única autoridade.

## Boss tags/IDs opcionais exigem cautela

Há problemas conhecidos de compatibilidade de tags/IDs de boss e referências opcionais. A wiki não presume reconhecimento universal de bosses externos.

## CI/testes ainda têm lacunas de hardening

O projeto registra lacunas de cobertura/infraestrutura que impedem tratar o estado atual como release hardening absoluto.

## Ordem de confiança

Quando houver divergência:

1. comportamento/código do `main`;
2. dados carregados;
3. testes atuais;
4. wiki;
5. planos.

## Fontes

- `AGENTS.md`
- `src/main/java/dev/gustavopere/rpgskilltree/core/`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/`
- `src/main/resources/data/rpgskilltree/`
