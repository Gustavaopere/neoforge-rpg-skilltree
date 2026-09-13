# Konkrete

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81338bc2eacbca10c4c7
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR, mod id, runtime e mixins confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Konkrete
- **Arquivo JAR:** `konkrete_neoforge_1.9.9_MC_1.21.jar`
- **Versão 1.21.1:** 1.9.9
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca técnica Client & Server que fornece contratos/utilitários reutilizáveis a mods consumidores; não é authority de gameplay, progressão ou world state.
- **Dependências:** NeoForge 1.21/1.21.1. Dependência operacional real é determinada pelos mods consumidores que declaram Konkrete; consumers não foram inferidos sem metadata.
- **Sobreposição:** Biblioteca específica. Não equivale a outras APIs do pack; eventual sobreposição ocorre apenas em hooks/utilitários ou mixins usados por consumers, não como sistema de gameplay.
- **Compatibilidade/Riscos:** Riscos principais: API/ABI drift entre linhas, mixin conflict, classloading client/server e quebra de consumers ao remover/atualizar isoladamente. Source JAR 1.9.9 existe, mas API pública não foi enumerada classe a classe nesta passagem.
- **Observações:** JAR físico `konkrete_neoforge_1.9.9_MC_1.21.jar`; mod id `konkrete`; runtime 1.9.9; mixins `konkrete.mixins.json` e `konkrete.neoforge.mixins.json`.
- **Procedência:** modlist.txt física atual (595 top-level) + CurseForge oficial file ID 5453385 + repositório/changelog oficial Konkrete; runtime NeoForge 1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/konkrete/files/5453385
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — corpo técnico reconstruído; identity/source pin, authority, side/lifecycle, mixin/API drift, risks e matriz de testes catalogados para 1.9.9.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `konkrete_neoforge_1.9.9_MC_1.21.jar`, mod id `konkrete`, versão `1.9.9`, Minecraft 1.21/1.21.1, NeoForge. É uma **biblioteca**, não um provider de gameplay.

## 1. Identidade e version pin
A modlist física confirma o JAR, mod id, nome e versão acima, além dos mixins `konkrete.mixins.json` e `konkrete.neoforge.mixins.json`. A publicação oficial CurseForge file ID 5453385 é a release NeoForge 1.9.9 para Minecraft 1.21 e 1.21.1 e publica também um source JAR correspondente. O repositório oficial separa versões por branches; a linha 1.21 é mantida separadamente das versões modernas.

## 2. Papel no modpack
Konkrete fornece infraestrutura reutilizável para mods consumidores do ecossistema do autor. Não registra uma economia, progressão, atributos, mobs ou world state que deva ser reproduzido pelos projetos próprios do pack.

## 3. Authority / ownership
A autoridade de Konkrete limita-se aos contratos de biblioteca que expõe e aos hooks/mixins necessários para esses contratos. Qualquer comportamento visível pertence ao mod consumidor que chama a API. Portanto, não atribuir a Konkrete features de UI, menus ou gameplay de um consumer apenas porque a biblioteca está carregada.

## 4. Superfície técnica confirmada
O histórico oficial do projeto registra utilitários de GUI/animação/eventos e bibliotecas auxiliares empacotadas ao longo da linha 1.x, além de deprecações de partes da API em preparação para versões posteriores. Para **1.9.9**, esta auditoria confirma de forma binária apenas a presença dos dois mixin configs e a identidade do mod; a lista completa de classes públicas do source JAR 1.9.9 não foi enumerada classe a classe. Não promover APIs históricas a contrato estável sem conferir o source pin quando um projeto próprio for compilá-las diretamente.

## 5. Client / server
CurseForge marca a release como **Client & Server**. A biblioteca pode servir consumidores em ambos os lados, mas classes de apresentação devem permanecer restritas ao cliente. Um consumer que force classloading de tipos client-only no dedicated server pode falhar mesmo que o JAR Konkrete seja aceito nos dois ambientes.

## 6. Lifecycle
Konkrete é infraestrutura de bootstrap: consumidores pressupõem que sua inicialização e mixins estejam disponíveis antes de usar seus contratos. Não há evidência nesta auditoria de state persistente próprio que exija migração de save. Reloads de resources/data devem ser tratados pelo consumer que mantém o state funcional correspondente.

## 7. Dependências embarcadas e top-level
Nenhum JarJar interno de Konkrete foi promovido a mod top-level nesta catalogação. Bibliotecas auxiliares citadas no repositório/changelog são implementação do host e não devem ganhar páginas independentes sem presença top-level na modlist.

## 8. Integração com a modlist
A modlist também contém `Melody 1.0.10`, outro projeto do mesmo autor, mas a coexistência não prova relação de dependência entre os dois. O conjunto de consumers que efetivamente declara Konkrete como required dependency não foi inferido apenas pelo inventário de JARs; para remoção/update, deve-se auditar metadata dos consumers.

## 9. Riscos técnicos
1. **API drift:** consumer compilado contra outra linha de Konkrete pode falhar por método/classe ausente.
2. **Mixin conflict:** hooks de biblioteca podem colidir com outro mod que altera o mesmo ponto vanilla.
3. **Side leakage:** consumer referencia classe client-only em dedicated server.
4. **Transitive assumptions:** remover Konkrete porque “não tem gameplay” pode derrubar consumers obrigatórios.
5. **Version skew:** atualizar a biblioteca isoladamente sem regressão dos consumers pode quebrar ABI/comportamento.

## 10. Matriz de testes
- [ ] Dedicated server inicia com Konkrete 1.9.9 e consumers atuais.
- [ ] Client inicia sem mixin/apply errors.
- [ ] Telas/recursos dos consumers que usam Konkrete abrem sem `NoClassDefFoundError`/`NoSuchMethodError`.
- [ ] Resource reload não duplica handlers de consumers.
- [ ] Update futuro é testado contra todos os consumers antes de substituir o JAR.
- [ ] Remoção só é tentada depois de auditar `neoforge.mods.toml`/metadata dos dependentes.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limites
- modlist física atual: JAR, mod id `konkrete`, versão 1.9.9 e mixin configs;
- CurseForge oficial: release NeoForge 1.9.9, suporte 1.21/1.21.1, Client & Server e source JAR correspondente;
- repositório/changelog oficial: arquitetura de biblioteca, versionamento por branches, histórico de APIs/deprecações.
A API pública 1.9.9 não foi enumerada integralmente nesta passagem; qualquer integração de código direto deve pinçar a classe/método no source JAR antes de implementação.
