# Just Enough Professions (JEP)

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db814c9e9ef041e96a152b
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JEP `4.0.5` e JEI `19.53.0.426` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Just Enough Professions (JEP)
- **Arquivo JAR:** `JustEnoughProfessions-neoforge-1.21.1-4.0.5.jar`
- **Versão 1.21.1:** 4.0.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL
- **Função:** Addon client-side de JEI que associa profissões de villagers às respectivas workstations/blocos registrados para consulta, sem criar profissões, villagers ou trades.
- **Dependências:** JEI como viewer funcional; pack usa JEI 19.53.0.426. Build 4.0.5 é Release NeoForge 1.21.1 e classificada client-side.
- **Sobreposição:** Complementa JEI e pode sobrepor apenas informação de guias/wikis. Não substitui o sistema vanilla/modded de POI, profession assignment ou trades.
- **Compatibilidade/Riscos:** Informativo. Riscos: workstation mapping stale/incompleto para profissão modded, JEI API drift, tags/POI alterados por outro mod e tradução divergente. O resultado real de profissão/trade continua server-authoritative.
- **Observações:** Changelog 4.0.5 adiciona traduções Argentine Spanish e Japanese; não publica alteração de gameplay. A ficha trata JEP como lookup UI de profissão↔workstation, não como provider de villagers/trades.
- **Procedência:** modlist.txt física atual + Modrinth/CurseForge oficiais Just Enough Professions 4.0.5 NeoForge 1.21.1 + changelog exato 4.0.5 + JEI físico 19.53.0.426.
- **Fonte:** https://modrinth.com/mod/just-enough-professions-jep/version/4.0.5
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Just Enough Professions 4.0.5 release-pinned; profession/workstation lookup, JEI boundary, client lifecycle/localization, modded-POI risks e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `JustEnoughProfessions-neoforge-1.21.1-4.0.5.jar`, mod id `justenoughprofessions`, versão `4.0.5`. É um addon informativo de JEI; não registra profissões ou trades de gameplay.

## 1. Papel e authority
Just Enough Professions apresenta, dentro do ecossistema JEI, qual workstation/bloco corresponde a uma profissão de villager. Minecraft ou o mod que registra a profissão/POI continua authority do vínculo real e dos trades.

## 2. Integração JEI
O pack usa JEI 19.53.0.426. JEP depende da API/registration do viewer para expor sua informação. Atualizar JEI é version gate para o plugin mesmo que villagers continuem funcionando normalmente.

## 3. Profissões modded
Profissões adicionadas por mods podem aparecer quando o registry/POI correspondente é compreendido pelo addon. Cobertura incompleta no viewer não remove a profissão do jogo. Não inventar suporte para um provider específico sem entry observada.

## 4. Workstation vs profissão real
Mostrar um bloco como workstation é lookup/UI. Profession assignment continua dependendo das regras server-side de villager, POI, distância, horário e disponibilidade. JEP não força villager a adquirir profissão.

## 5. Trades
O addon não cria nem balanceia trades. Mesmo quando a profissão é mostrada corretamente, conteúdo/preço de trades continua sob Minecraft/mod provider e event/data modifiers externos.

## 6. Release 4.0.5
A mudança publicada para 4.0.5 é localização: **Argentine Spanish** e **Japanese**. Não há evidência na release de alteração de gameplay ou algoritmo de profissão; não atribuir outras mudanças à build.

## 7. Client / server
A build é classificada client-side. O cliente renderiza lookup e texto; profession/POI/trades permanecem state do servidor. A interface não é autorização nem causa de assignment.

## 8. Lifecycle
Validar client boot, JEI plugin registration, world join, registry/recipe reload e mudança de idioma. Entradas de profissão precisam reconstruir sem duplicação e sem referências órfãs.

## 9. Riscos técnicos
- workstation mapping stale;
- profissão modded sem entry;
- JEI API drift;
- POI/tag alterado sem atualização do display;
- localization key ausente;
- UI confundida com regra server-side;
- duplicate entry após reload.

## 10. Matriz de testes obrigatória
- [ ] Cliente inicia com JEP 4.0.5 + JEI 19.53.0.426.
- [ ] Profissões vanilla exibem workstations corretas.
- [ ] Amostra de profissão modded não causa crash.
- [ ] Profession assignment real continua obedecendo ao servidor.
- [ ] Trade list não é alterada por JEP.
- [ ] Argentine Spanish/Japanese carregam sem missing-key crítico.
- [ ] Language/resource reload não duplica entries.
- [ ] Update futuro de JEI é smoke-tested antes de adoção.

## 11. Evidências e limites
- **Modlist física:** JAR/mod id/version e JEI atual.
- **Modrinth/CurseForge oficiais:** Release 4.0.5 NeoForge 1.21.1, ambiente client-side e função profession/workstation.
- **Changelog 4.0.5:** duas traduções adicionadas.
- **Limite:** não foi extraído catálogo de todas as professions modded reconhecidas no runtime.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
