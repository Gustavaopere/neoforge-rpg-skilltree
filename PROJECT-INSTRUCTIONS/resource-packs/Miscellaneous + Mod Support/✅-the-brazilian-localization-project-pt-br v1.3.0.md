# The Brazilian Localization Project [PT-BR]

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack
- **Arquivo:** `The Brazilian Project [1.21.1-1.3.0].zip`
- **Versão 1.21.1:** 1.3.0
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra captura física da pasta Resource Packs do perfil em 08/09/2026 como evidência de instalação de `The Brazilian Project [1.21.1-1.3.0].zip`.
- A modlist física JAR-centric de 08/09/2026 não constitui prova independente da presença do `.zip`.
- O pack controla somente recursos de localização/strings; cada mod permanece authority integral de registries, recipes, stats, AI, networking e demais mecânicas.

## Propriedades do banco

- **Mod:** The Brazilian Localization Project [PT-BR]
- **Arquivo JAR:** `The Brazilian Project [1.21.1-1.3.0].zip`
- **Tipo de conteúdo:** Resource Pack
- **Versão 1.21.1:** 1.3.0
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL
- **Função:** Resource pack de localização PT-BR para Minecraft modded que preenche traduções ausentes e revisa inconsistências/terminologia de traduções existentes.
- **Dependências:** Minecraft 1.21.1 e os mods cujas localization keys o pack traduz. Não cria dependência funcional; ausência de tradução deve cair em chave/idioma fallback sem alterar gameplay.
- **Sobreposição:** Pode substituir `pt_br.json`/localization resources fornecidos pelos próprios mods ou por outros translation packs. A prioridade de resource pack determina qual string vence por chave.
- **Compatibilidade/Riscos:** Pack de tradução amplo, mas não equivale a 100% de cobertura de todos os mods instalados. Pode sobrescrever traduções PT-BR nativas por prioridade e ficar desatualizado quando mods adicionam/renomeiam localization keys.
- **Observações:** Arquivo instalado `The Brazilian Project [1.21.1-1.3.0].zip`, versão 1.3.0. A lista oficial 1.21.1 cobre muitos mods presentes no pack, mas não autoriza afirmar cobertura total da modlist.
- **Procedência:** CurseForge oficial The Brazilian Localization Project 1.3.0 para 1.21.1 + captura Resource Packs do perfil em 08/09/2026 + modlist física atual.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/brazilian-project
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Brazilian Localization Project 1.3.0, PT-BR modded, coverage boundary, language-resource priority, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado:** `The Brazilian Project [1.21.1-1.3.0].zip`, versão `1.3.0`, publicado para Minecraft 1.21.1.

## 1. Papel e authority
The Brazilian Localization Project fornece localização **Português do Brasil** para mods, preenchendo traduções ausentes e revisando inconsistências, terminologia e naturalidade onde o projeto possui cobertura. Os mods continuam authorities de conteúdo e gameplay.

## 2. Cobertura confirmada
A lista oficial 1.21.1 é ampla e inclui vários mods presentes neste pack, como Alex's Mobs, Apotheosis/Apothic modules, Ars Nouveau e addons, Artifacts, Balm, Better Combat, BlockUI, Bookshelf, Bosses' Rise, Cosmetic Armor Reworked e Curios, entre muitos outros. **Lista ampla não significa 100% da modlist.**

## 3. Modelo de localização
O pack fornece/substitui localization resources, tipicamente chaves `pt_br`. Quando uma mesma chave existe no mod e neste pack, a prioridade efetiva dos resources determina qual texto é exibido.

## 4. Boundary funcional
Tradução ausente, incorreta ou desatualizada deve resultar em texto fallback/chave sem tradução; não deve mudar registry IDs, recipes, stats, AI, networking ou lógica de qualquer mod.

## 5. Atualização e drift
Mods atualizados podem adicionar/renomear localization keys antes que o projeto 1.3.0 as cubra. O catálogo não presume que uma entrada oficial de suporte esteja completa para toda versão posterior do mod.

## 6. Sobreposição e riscos
1. Tradução do mod ser mais nova que a versão do pack.
2. Outro pack PT-BR sobrescrever a mesma chave.
3. Terminologia ficar inconsistente entre mods integrados.
4. Chave nova aparecer em inglês ou como literal.
5. Tradução alterar sentido técnico de configuração/tooltips sem alterar o valor real.

## 7. Matriz de testes
- [ ] Configurar idioma para Português do Brasil.
- [ ] Amostrar menus, items, tooltips e mensagens de vários mods suportados.
- [ ] Conferir termos técnicos recorrentes entre Ars, Apotheosis e RPG/combat mods.
- [ ] Identificar chaves faltantes/inglesas após updates recentes.
- [ ] Comparar prioridade contra outros translation packs, se presentes.
- [ ] Resource reload/relog sem erro de language JSON.
- [ ] Confirmar que pack on/off não altera qualquer dado de gameplay.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma a versão 1.3.0 para 1.21.1, o objetivo de completar PT-BR modded e a extensa lista de mods cobertos. O catálogo não extrapola essa lista para cobertura total do perfil.

> Boundary canônico: **o projeto controla somente strings/localização; cada mod continua controlando integralmente seu conteúdo e comportamento**.

## 9. Atualização upstream 1.3.0 → 1.4.0 — 10/10/2026

**Autoridade física histórica:** `The Brazilian Project [1.21.1-1.3.0].zip` conforme captura de Resource Packs do perfil em 08/09/2026. Essa versão não foi substituída nem certificada mediante novo ZIP nesta auditoria.

**Publicação oficial:** CurseForge `The Brazilian Project [1.21.1-1.4.0].zip`, **07/10/2026**, file ID **9085941**, compatível com Minecraft 1.21.1. Não foi localizada publicação `1.3.1` para **Minecraft 1.21.1** na fonte consultada; essa numeração existe em **Minecraft 26.1.2** e não deve ser inserida artificialmente na cadeia desta linha. A sequência comprovada para 1.21.1 desde o ZIP físico é **1.3.0 → 1.4.0**.

### Changelog publicado pelo autor da 1.4.0
- **Suporte a 231 mods para Minecraft 1.21.1**, conforme declaração literal do autor. Não inferir que os 231 sejam necessariamente novos em relação à 1.3.0, porque a lista de cobertura anterior não foi comparada item a item.
- **Mods atualizados** (traduções revisadas) e correções de **typos, bugs e ajustes diversos**.
- A relação nominal de novos projetos anunciados inclui entradas diretamente relevantes ao pack e/ou sua documentação, como **Aeronautics Curios Compat**, **Ars Nouveau Flavors Delight**, **Create Aeronautics Gadgets & Gizmos**, **Create Nuclear**, **Just Enough Items**, **Sophisticated Backpacks**, **Sophisticated Core**, **Sable** e outros. **MineColonies não foi localizado na lista nominal da release** e não deve ser tratado como cobertura confirmada. Não garantir que todas as traduções estejam completas nem que os mods instalados tenham a mesma versão da linha-alvo da tradução.

### Integração com o pack
A alteração é de **arquivos de idioma, tooltips e textos de UI** e não modifica AI, damage, recipes, registries, configs efetivos ou persistência server-side. A última modlist física contém diversas atualizações documentais upstream não instaladas; os textos de 1.4.0 podem antecipar ou não cobrir translation keys específicas de novas versões. Em ordem de prioridade, testar pack 1.4.0 acima/abaixo de outros packs pt_BR, abrir as interfaces de Create, Ars, MineColonies, JEI, Sophisticated e quests; conferir placeholders `%s`, format arguments, JSON válido, keys cruas em inglês, texto truncado, tooltips, HUD, tela de configs e recuperação após F3+T. **Não confundir prioridade visual com autoridade de dados de gameplay.**

**Fonte oficial:** https://www.curseforge.com/minecraft/texture-packs/brazilian-project/files/9085941

**Estado:** última publicação 1.4.0 registrada; **ZIP físico histórico 1.3.0 preservado**, testes pendentes.

