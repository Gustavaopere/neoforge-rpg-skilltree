# Kiwi

## Propriedades do registro

- **Mod:** Kiwi
- **Arquivo JAR:** Kiwi-1.21.1-NeoForge-15.8.7.jar
- **Versão 1.21.1:** 15.8.7+neoforge
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kiwi/files/8423062
- **Função:** Biblioteca/modding toolkit da Snownee com infraestrutura para consumers e módulos/QoL configuráveis próprios; no pack é dependência concreta de Lychee Tweaker 6.7.0.
- **Dependências:** NeoForge 1.21.1. Consumer físico confirmado: Lychee Tweaker 6.7.0 declara Kiwi como dependência. O JAR físico Kiwi 15.8.7 embarca `fabric-api-base-0.4.42+d1308ded19.jar` via JarJar como runtime interno do host.
- **Compatibilidade/Riscos:** Library + módulos configuráveis. Riscos: consumer ABI drift, data-module enable/disable inconsistente, config/UI mismatch, KSwitch viewer integration drift e opções client-side confundidas com regras de servidor. 15.8.7 corrige especificamente o toggle do data module.
- **Sobreposição:** Biblioteca específica, não substituível por Architectury/Balm/etc. sem suporte do consumer. Lychee 6.7.0 é dependente concreto; features QoL próprias podem sobrepor apenas UX/config, não justificando remoção da API.
- **Observações:** Runtime `15.8.7+neoforge` continua atual para 1.21.1. O JarJar `fabric-api-base 0.4.42+d1308ded19` não recebe entrada top-level. 15.8.7 corrige data module toggle; demais regression gates da linha permanecem preservados.
- **Procedência:** modlist.txt física anexada e reconferida em 12/09/2026 + CurseForge oficial Kiwi 15.8.7 NeoForge 1.21.1 file 8423062 + changelog já auditado + inventário físico do JAR confirmando `fabric-api-base-0.4.42+d1308ded19.jar` em `META-INF/jarjar`.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — Kiwi 15.8.7+neoforge/JAR físico reconfirmado; 15.8.7 permanece a release NeoForge 1.21.1 mais recente localizada. Correção material: `fabric-api-base 0.4.42+d1308ded19` embarcado via JarJar foi documentado como dependência interna, não top-level.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #358: JAR `Kiwi-1.21.1-NeoForge-15.8.7.jar`, mod id `kiwi`, runtime `15.8.7+neoforge`, SHA-1 `d5c1d9f814bf146459f6e5e169c744c3e5bc184e`.

<callout icon="🥝" color="green_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `Kiwi-1.21.1-NeoForge-15.8.7.jar`, mod id `kiwi`, metadata `15.8.7+neoforge`. CurseForge file 8423062 é Release NeoForge 1.21.1. **Lychee Tweaker 6.7.0**, presente no pack, declara Kiwi como dependência.
</callout>
## 1. Papel e authority
Kiwi é biblioteca/modding toolkit da Snownee. Fornece infraestrutura reutilizável a consumers e também possui módulos/QoL configuráveis próprios. O gameplay específico de Lychee ou outro consumer continua pertencendo ao respectivo mod.
## 2. Consumer físico — Lychee e runtime embarcado
O pack contém `Lychee-1.21.1-NeoForge-6.7.0.jar`. O projeto Lychee declara **Kiwi** como dependência. Portanto Kiwi não pode ser removido por parecer “só uma library” sem quebrar a dependency graph atual.
O JAR físico `Kiwi-1.21.1-NeoForge-15.8.7.jar` também contém `META-INF/jarjar/fabric-api-base-0.4.42+d1308ded19.jar`, identificado como **Forgified Fabric API Base**. Pelo protocolo do catálogo, esse componente é runtime interno JarJar do Kiwi e **não recebe entrada top-level**. Atualizações do host podem trocar esse componente sem criar um novo arquivo na pasta `mods`.
## 3. Data module
A linha atual possui um **data module** configurável. A 15.8.6 passou a deixá-lo desabilitado por padrão; a 15.8.7 corrige especificamente o **data module toggle**. Sem ler a config local, não se afirma se o módulo está habilitado no pack.
## 4. Configuração e telas
A documentação pública descreve opções configuráveis e integração com Cloth Config quando disponível. Config UI é apresentação; cada opção deve respeitar seu side/escopo real. Não transformar uma preferência client-side em regra server-side.
## 5. QoL próprios
A página do projeto documenta recursos configuráveis próprios, incluindo exibição de NBT/tags em tooltips, controle de telemetria Microsoft, calculadora `/kalc`, comando `/kiwic quiet`, opções de warning experimental e efeitos de fade. Esses recursos coexistem com o papel principal de library.
## 6. KSwitch e viewers
A 15.8.3 adicionou suporte JEI/EMI para **KSwitch groups** e configure command. O pack possui JEI 19.56.0.440; presença de suporte EMI não significa que EMI esteja instalado.
## 7. Release 15.8.5–15.8.7
- 15.8.5: corrige crash ao pressionar F3 para mostrar informação em certos itens.
- 15.8.6: data module desabilitado por padrão e fix do mod id de Cloth Config em NeoForge.
- 15.8.7: fix do toggle do data module.
Esses são regression gates acumulados da linha instalada.
## 8. Client / server
Kiwi pode hospedar APIs consumidas em ambos os lados e recursos puramente client-side. O side efetivo de cada módulo/consumer deve ser respeitado. Dedicated server não deve carregar GUI/render code por path comum indevido.
## 9. Data e reload
Consumers podem usar infraestrutura data-driven. Data reload precisa preservar schema/IDs dos consumers e não registrar handlers duas vezes. Alterar o data module pode mudar quais recursos Kiwi disponibiliza; validar após restart/reload suportado.
## 10. Lifecycle
Validar construction, config parse, data load, JEI/KSwitch registration, world join, `/reload` quando suportado, reconnect e server restart. Lychee deve continuar carregando após qualquer mudança de Kiwi.
## 11. Riscos técnicos
- Lychee falhar por ABI/version drift;
- data module toggle não refletir o state configurado;
- módulo desabilitado deixar data parcialmente registrado;
- config UI/mod id incorreto;
- F3 tooltip crash regressar;
- KSwitch/JEI registration duplicar após reload;
- feature client-side carregada em dedicated server;
- remover Kiwi mantendo consumer obrigatório.
## 12. Matriz de testes obrigatória
- [ ] Dedicated server e cliente iniciam com Kiwi 15.8.7 + Lychee 6.7.0.
- [ ] Lychee registra recipes/data sem linkage error.
- [ ] Data module toggle apresenta o state esperado — regressão 15.8.7.
- [ ] Data module desabilitado não deixa half-registered resources.
- [ ] F3 em amostra de itens não reproduz crash corrigido na 15.8.5.
- [ ] JEI/KSwitch groups registram sem duplicate entries.
- [ ] `/kalc` e `/kiwic quiet`, se habilitados/permitidos, não afetam gameplay authority externa.
- [ ] Resource/data reload não duplica listeners.
- [ ] Dedicated server não carrega classes client-only indevidamente.
- [ ] Update futuro de Kiwi é testado com Lychee antes de adoção.
## 13. Evidências e limites
- **Modlist física:** JAR/mod id/version, Lychee 6.7.0 presente e `fabric-api-base-0.4.42+d1308ded19.jar` embarcado em `META-INF/jarjar`.
- **CurseForge oficial:** file 8423062 e changelog 15.8.3–15.8.7.
- **Projeto Lychee oficial:** Kiwi como dependency.
- **Limite:** config Kiwi real do pack não foi fornecida; estado de módulos/opções não é presumido.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
